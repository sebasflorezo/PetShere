# Imagen base de node para correr Angular19
FROM node:20-alpine

# Directorio de la app
WORKDIR /app

# Copia todo lo de la carpeta
COPY . .

# Puerto de la app
EXPOSE 4200

# Instalar las dependencias del proyecto
RUN npm install

# Ejecutar comando de inicio
CMD ["npm", "run", "full:start"]
