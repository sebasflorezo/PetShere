package com.PetShere.service.implementation.facture;

import com.PetShere.persistence.model.facture.Facture;
import com.PetShere.persistence.repository.facture.IFactureRepository;
import com.PetShere.presentation.dto.facture.FactureDto;
import com.PetShere.service.exception.ResourceNotFoundException;
import com.PetShere.service.interfaces.IFactureService;
import com.PetShere.util.Constants;
import com.PetShere.util.mapper.facture.FactureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactureServiceImpl implements IFactureService {

    private final IFactureRepository factureRepository;

    public FactureDto getFactureById(Long id) {
        FactureDto facture = factureRepository.findById(id)
                .map(FactureMapper::toDto)
                .filter(this::isFactureFromClient)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.NOT_FOUND_GENERIC));

        return (facture == null || !isFactureFromClient(facture)) ? null : facture;
    }

    public List<FactureDto> getFacturesClient(Long id) {
        // TODO: Retornar las facturas del cliente
        // + Validar además que el cliente esté pidiendo sus facturas o retornar directamente sus facturas
        return List.of();
    }

    public Facture createFacture(FactureDto factureDto) {
        // TODO: Crear factura
        return null;
    }

    private boolean isFactureFromClient(FactureDto factureDto) {
        // TODO: Implementar
        return true;
    }
}
