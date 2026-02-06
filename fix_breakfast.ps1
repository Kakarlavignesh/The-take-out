
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
try {
    $r = Invoke-RestMethod 'https://foodish-api.com/api/images/dosa'
    Invoke-WebRequest $r.image -OutFile 'c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images\breakfast.jpg'
    Write-Host "Fixed breakfast.jpg"
}
catch {
    Write-Warning "Failed to fix breakfast.jpg: $_"
}
