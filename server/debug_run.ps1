$ErrorActionPreference = "Stop"

Write-Host "---------------------------------------------------" -ForegroundColor Cyan
Write-Host "   THE TAKEOUT - AUTO SETUP & RUN" -ForegroundColor Cyan
Write-Host "---------------------------------------------------"
Write-Host ""

# 1. Define Paths
$MavenVersion = "3.9.6"
$MavenUrl = "https://archive.apache.org/dist/maven/maven-3/$MavenVersion/binaries/apache-maven-$MavenVersion-bin.zip"
$WorkDir = Get-Location
$MavenDir = Join-Path $WorkDir ".mvn_portable"
$MavenBin = Join-Path $MavenDir "apache-maven-$MavenVersion\bin"
$MvnCmd = Join-Path $MavenBin "mvn.cmd"

# 2. Check/Install Maven
if (-not (Test-Path $MvnCmd)) {
    Write-Host "[INFO] Portable Maven not found. Downloading..." -ForegroundColor Yellow
    
    # Download
    $ZipPath = Join-Path $WorkDir "maven.zip"
    Invoke-WebRequest -Uri $MavenUrl -OutFile $ZipPath
    
    # Extract
    Write-Host "[INFO] Extracting Maven..." -ForegroundColor Yellow
    Expand-Archive -Path $ZipPath -DestinationPath $MavenDir -Force
    
    # Cleanup
    Remove-Item $ZipPath -Force
    
    Write-Host "[OK] Maven Installed to $MavenDir" -ForegroundColor Green
}
else {
    Write-Host "[OK] Portable Maven found." -ForegroundColor Green
}

# 3. Add to PATH for this session
$env:Path = "$MavenBin;$env:Path"
$env:M2_HOME = Join-Path $MavenDir "apache-maven-$MavenVersion"

# 4. Run Server
Write-Host ""
Write-Host "Starting Spring Boot Server..." -ForegroundColor Cyan
Write-Host "Please wait for 'Started TheTakeoutApplication'..." -ForegroundColor Gray
Write-Host ""

# Run clean first to ensure updates (like new images/data) are processed
& $MvnCmd spring-boot:run

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "[ERROR] Server failed to start." -ForegroundColor Red
    Read-Host "Press Enter to exit..."
}
