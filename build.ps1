# build.ps1

# Change to the directory where the script is located
Set-Location -Path $PSScriptRoot

# Set Gradle user home directory to within the project
$env:GRADLE_USER_HOME = "$PSScriptRoot\.gradle"

# Check Gradle version
Write-Host "Checking Gradle version..."
& .\gradlew --version
Write-Host "Exit code: $LASTEXITCODE"
if ($LASTEXITCODE -ne 0) {
    Write-Host "Failed to check Gradle version"
    exit 1
}

# Clean existing build directory
Write-Host "Cleaning existing build directory..."
& .\gradlew clean --offline
Write-Host "Exit code: $LASTEXITCODE"
if ($LASTEXITCODE -ne 0) {
    Write-Host "Failed to clean existing build directory"
    exit 1
}

# Build project
Write-Host "Building the project..."
& .\gradlew build --offline
Write-Host "Exit code: $LASTEXITCODE"
if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed"
    exit 1
}

# Check result
if ($LASTEXITCODE -eq 0) {
    Write-Host "Build successful"
} else {
    Write-Host "Error occurred during build"
    exit 1
}
