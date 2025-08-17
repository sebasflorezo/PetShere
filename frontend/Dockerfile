# Etapa 1: build
# Imagen base de node para correr Angular19
FROM node:20-alpine AS build

# Directorio de la app
WORKDIR /app

# Copia todo de la carpeta a /app
COPY . .

# Instalar las dependencias del proyecto
RUN npm install

# Puerto de la app
EXPOSE 4200

# Ejecutar comando de inicio
ENTRYPOINT ["npm", "run", "full:start"]