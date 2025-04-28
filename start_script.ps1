$ErrorActionPreference = "Stop"

function start_env {
  if (-not (Test-Path ".env")) {
    Write-Host "Error: Archivo .env no encontrado" -ForegroundColor Red
    exit 1
  }

  Get-Content ".env" | ForEach-Object {
    if ($_ -match "^\s*([^#][^=]+)=(.+)$") {
      $key = $matches[1].Trim()
      $value = $matches[2].Trim()
      Set-Item -Path "Env:$key" -Value $value
    }
  }
}

function cleanup {
  Write-Host "`nDeteniendo procesos..." -ForegroundColor Red

  if ($null -ne $PETSHERE_BACKEND_PID) {
    if (Get-Process -Id $PETSHERE_BACKEND_PID -ErrorAction SilentlyContinue) {
      Stop-Process -Id $PETSHERE_BACKEND_PID -Force
    }
  }

  if ($null -ne $PETSHERE_FRONTEND_PID) {
    if (Get-Process -Id $PETSHERE_FRONTEND_PID -ErrorAction SilentlyContinue) {
      Stop-Process -Id $PETSHERE_FRONTEND_PID -Force
    }
  }

  try { sudo net stop mysql80 }
  catch { Write-Host "No se pudo detener MySQL`n" -ForegroundColor Yellow }

  Write-Host "Aplicación detenida`n" -ForegroundColor Red
  exit 0
}

# SCRIPT START
Register-EngineEvent -SourceIdentifier ConsoleCancel -Action {
  cleanup
} | Out-Null

try {
  Write-Host @"
██████╗ ███████╗████████╗███████╗██╗  ██╗███████╗██████╗ ███████╗
██╔══██╗██╔════╝╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔══██╗██╔════╝
██████╔╝█████╗     ██║   ███████╗███████║█████╗  ██████╔╝█████╗
██╔═══╝ ██╔══╝     ██║   ╚════██║██╔══██║██╔══╝  ██╔══██╗██╔══╝
██║     ███████╗   ██║   ███████║██║  ██║███████╗██║  ██║███████╗
╚═╝     ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝
---------- Developed by Johan Sebastian Florez Ospina ----------

"@ -ForegroundColor Green

  Write-Host "Inicializando procesos de aplicación  PetShere...`n" -ForegroundColor Yellow

  # Start env
  Write-Host "Inicializando variables de entorno..." -ForegroundColor Yellow
  start_env
  Write-Host "Variables de entorno inicializadas exitosamente`n" -ForegroundColor Green

  # Start MySql
  Write-Host "Inicializando base de datos..." -ForegroundColor Yellow
  sudo net start mysql80
  Write-Host "Base de datos inicializada exitosamente`n" -ForegroundColor Green

  # Start Backend
  Write-Host "Inicializando backend con Maven y SpringBoot..." -ForegroundColor Yellow
  $backendProcess = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -PassThru -NoNewWindow -RedirectStandardOutput "backend.log" -RedirectStandardError "backend-error.log"
  $PETSHERE_BACKEND_PID = $backendProcess.Id
  Write-Host "Backend inicializado con el PID: $PETSHERE_BACKEND_PID `n" -ForegroundColor Green

  # Start Frontend
  Push-Location "./frontend"
    Write-Host "Inicializando frontend con Angular..." -ForegroundColor Yellow
    $frontendProcess = Start-Process -FilePath "npm.cmd" -ArgumentList "run full:start" -PassThru -NoNewWindow -RedirectStandardOutput "../frontend.log" -RedirectStandardError "../frontend-error.log"
    $PETSHERE_FRONTEND_PID = $frontendProcess.Id
    Write-Host "Frontend inicializado con el PID: $PETSHERE_FRONTEND_PID `n" -ForegroundColor Green
  Pop-Location

  Write-Host "Ejecutando aplicación completamente" -ForegroundColor Green

  Wait-Process -Id $PETSHERE_BACKEND_PID, $PETSHERE_FRONTEND_PID
} catch {
  Write-Host "`nError al iniciar la aplicación: $($_.Exception.Message)" -ForegroundColor Red
  throw
} finally {
  cleanup
}
# SCRIPT END