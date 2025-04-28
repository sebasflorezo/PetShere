#!/bin/bash
set -e

RED="\e[31m"
GREEN="\e[32m"
YELLOW="\e[33m"
RESET="\e[0m"

start_env() {
 if [[ ! -f .env ]]; then
   echo -e "${RED}Error: Archivo .env no encontrado${RESET}"
   exit 1
 fi

 set -a
 source .env
 set +a
}

cleanup () {
  echo -e "${RED}\nDeteniendo procesos...${RESET}"
  kill $PETSHERE_BACKEND_PID $PETSHERE_FRONTEND_PID
  # TODO Kill mysql demon
  echo -e "${RED}Aplicación detenida\n${RESET}"
  exit 0
}

trap cleanup SIGINT

# SCRIPT START
{
  echo -e "${GREEN}
  ██████╗ ███████╗████████╗███████╗██╗  ██╗███████╗██████╗ ███████╗
  ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔══██╗██╔════╝
  ██████╔╝█████╗     ██║   ███████╗███████║█████╗  ██████╔╝█████╗
  ██╔═══╝ ██╔══╝     ██║   ╚════██║██╔══██║██╔══╝  ██╔══██╗██╔══╝
  ██║     ███████╗   ██║   ███████║██║  ██║███████╗██║  ██║███████╗
  ╚═╝     ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝
  ---------- Developed by Johan Sebastian Florez Ospina ----------
  \n${RESET}"

  echo -e "${YELLOW}Inicializando variables de entorno...\n${RESET}"

  # Start env
  echo -e "${YELLOW}Inicializando variables de entorno...${RESET}"
  start_env
  echo -e "${GREEN}Variables de entorno inicializadas exitosamente \n ${RESET}"

  # Start mysql
  echo -e "${YELLOW}Inicializando base de datos...${RESET}"
  # TODO Call mysql demon
  echo -e "${GREEN}Base de datos inicializada exitosamente \n ${RESET}"

  # Start backend
  echo -e "${YELLOW}Inicializando backend con Maven y SpringBoot....${RESET}"
  mvn spring-boot:run > backend.log 2>&1 &
  PETSHERE_BACKEND_PID=$!
  echo -e "${GREEN}Backend inicializado con el PID: $PETSHERE_BACKEND_PID \n ${RESET}"

  # Start frontend
  {
    echo -e "${YELLOW}Inicializando frontend con Angular...${RESET}"
    cd ./frontend/ &&
    npm run full:start > ../frontend.log 2>&1 &
  } &
  PETSHERE_FRONTEND_PID=$!
  echo -e "${GREEN}Frontend inicializado con el PID: $PETSHERE_FRONTEND_PID \n ${RESET}"

  tail -f backend.log frontend.log &

  wait $PETSHERE_BACKEND_PID
  wait $PETSHERE_FRONTEND_PID
} || {
  echo "${RED}\nError al iniciar la aplicación${RESET}"
}
# SCRIPT END
