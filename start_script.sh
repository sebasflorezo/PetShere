#!/bin/bash
set -euo pipefail
stty -echoctl

# Colors
RED="\e[31m"
GREEN="\e[32m"
YELLOW="\e[33m"
RESET="\e[0m"

# Processes PID
PETSHERE_BACKEND_PID=""
PETSHERE_FRONTEND_PID=""
TAIL_PID=""

# Log files
BACKEND_LOG="backend.log"
FRONTEND_LOG="frontend.log"

# Flags
VERBOSE=false

for arg in "$@"; do
  case $arg in 
    --verbose)
      VERBOSE=true
      ;;

    *)
      echo -e "${RED}Error: opción no reconocida: '$arg'${RESET}" 
      exit 1
      ;;
  esac
done

cleanup () {
  echo -e "${RED}\nDeteniendo procesos...${RESET}"
  [[ -n "$PETSHERE_BACKEND_PID" ]] && kill "$PETSHERE_BACKEND_PID" 2>/dev/null || true
  [[ -n "$PETSHERE_FRONTEND_PID" ]] && kill "$PETSHERE_FRONTEND_PID" 2>/dev/null || true
    [[ -n "$TAIL_PID" ]] && kill "$TAIL_PID" 2>/dev/null || true
  sudo systemctl stop mysql
  echo -e "${RED}Aplicación detenida\n${RESET}"
  read -n 1 -s -r -p "Presione cualquier tecla para continuar..."
  stty echoctl
  exit 0
}

trap cleanup SIGINT

check_dependencies() {
  local dependencies=("mvn" "npm" "systemctl" "mysql")

  for cmd in "${dependencies[@]}"; do
    command -v $cmd >/dev/null 2>&1 || {
      echo -e "${RED}Error: '$cmd' no está instalado o no se encuentra en el PATH${RESET}"
      exit 1
    }
  done
}

start_env() {
 if [[ ! -f .env ]]; then
   echo -e "${RED}Error: Archivo .env no encontrado${RESET}"
   exit 1
 fi

 set -a
 source .env
 set +a
}

print_banner() {
  echo -e "${GREEN}
  ██████╗ ███████╗████████╗███████╗██╗  ██╗███████╗██████╗ ███████╗
  ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔══██╗██╔════╝
  ██████╔╝█████╗     ██║   ███████╗███████║█████╗  ██████╔╝█████╗
  ██╔═══╝ ██╔══╝     ██║   ╚════██║██╔══██║██╔══╝  ██╔══██╗██╔══╝
  ██║     ███████╗   ██║   ███████║██║  ██║███████╗██║  ██║███████╗
  ╚═╝     ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝
  ---------- Developed by Johan Sebastian Florez Ospina ----------
  \n${RESET}"
}

start_backend() {
  mvn spring-boot:run > backend.log 2>&1 & 
  PETSHERE_BACKEND_PID=$!
}

start_frontend() {
  (
    cd ./frontend/ &&
    npm run full:start > ../frontend.log 2>&1
  ) &
  PETSHERE_FRONTEND_PID=$!
}

main () {
  print_banner
  check_dependencies
  echo -e "${YELLOW}Inicializando aplicación...\n${RESET}"

  # Start env
  echo -e "${YELLOW}Inicializando variables de entorno...${RESET}"
  start_env
  echo -e "${GREEN}Variables de entorno inicializadas exitosamente \n ${RESET}"

  # Start mysql
  echo -e "${YELLOW}Inicializando base de datos...${RESET}"
  sudo systemctl start mysql
  echo -e "${GREEN}Base de datos inicializada exitosamente \n ${RESET}"

  # Start backend
  echo -e "${YELLOW}Inicializando backend con Maven y SpringBoot....${RESET}"
  start_backend
  echo -e "${GREEN}Backend inicializado con el PID: $PETSHERE_BACKEND_PID \n ${RESET}"

  # Start frontend
  echo -e "${YELLOW}Inicializando frontend con Angular...${RESET}"
  start_frontend
  echo -e "${GREEN}Frontend inicializado con el PID: $PETSHERE_FRONTEND_PID \n ${RESET}"

  if [[ "$VERBOSE" = true ]]; then
    echo -e "${YELLOW}Mostrando logs en tiempo real...\n${RESET}"
    tail -f "$BACKEND_LOG" "$FRONTEND_LOG" &
    TAIL_PID=$!
  fi

  echo -e "${GREEN}Aplicación lanzada exitosamente :)\n ${RESET}"
  wait $PETSHERE_BACKEND_PID
  wait $PETSHERE_FRONTEND_PID
} 

main || {
  echo -e "${RED}\nError al iniciar la aplicación${RESET}" >&2
  cleanup

}
