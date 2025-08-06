package com.PetShere.service.implementation.facture;

import com.PetShere.persistence.model.facture.Facture;
import com.PetShere.persistence.model.facture.PaymentMethod;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.facture.IFactureRepository;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.presentation.dto.facture.FactureDto;
import com.PetShere.service.exception.AuthorizationDeniedException;
import com.PetShere.service.exception.ResourceNotFoundException;
import com.PetShere.service.interfaces.IFactureService;
import com.PetShere.util.AppUtil;
import com.PetShere.util.Constants;
import com.PetShere.util.Validations;
import com.PetShere.util.mapper.facture.FactureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureServiceImpl implements IFactureService {

    private final IFactureRepository factureRepository;
    private final IUserRepository userRepository;

    public List<FactureDto> getAllFactures() {
        return factureRepository.findAll()
                .stream()
                .map(FactureMapper::toDto)
                .collect(Collectors.toList());
    }

    public FactureDto getFactureById(Long id) {
        FactureDto facture = factureRepository.findById(id)
                .map(FactureMapper::toDto)
                .filter(this::isFactureFromClient)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.NOT_FOUND_GENERIC));

        return (facture == null || !isFactureFromClient(facture)) ? null : facture;
    }

    public List<FactureDto> getFacturesByClientId(String document) {
        Optional<User> user = AppUtil.getCurrentUser(userRepository);
        if (user.isEmpty() || !user.get().getDocument().equals(document))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        return factureRepository.getFacturesByClientId(user.get().getId())
                .stream()
                .map(FactureMapper::toDto)
                .collect(Collectors.toList());
    }

    public Facture createFacture(String document, String paymentMethod) {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER));

        if (!document.equals(user.getDocument()))
            throw new AuthorizationDeniedException(Constants.UNAUTHORIZED_USER);

        Validations.validateFacture(document, String.valueOf(paymentMethod));
        Facture facture = Facture.builder()
                .client(user)
                .totalAmount(0.0)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .build();

        factureRepository.save(facture);
        return facture;
    }

    private boolean isFactureFromClient(FactureDto factureDto) {
        return AppUtil.getCurrentUser(userRepository)
                .map(user -> factureRepository.existsByIdAndClientId(factureDto.getId(), user.getId()))
                .orElse(false);
    }
}
