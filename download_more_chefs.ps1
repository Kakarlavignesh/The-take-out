$chefs = @(
    @{ Name = "chef_elena"; Url = "https://images.unsplash.com/photo-1556910103-1c02745a30bf?q=80&w=600&auto=format&fit=crop" },
    @{ Name = "chef_kenji"; Url = "https://images.unsplash.com/photo-1607631568010-a87245c0daf8?q=80&w=600&auto=format&fit=crop" },
    @{ Name = "chef_isabella"; Url = "https://images.unsplash.com/photo-1595273670150-bd0c3c392e46?q=80&w=600&auto=format&fit=crop" },
    @{ Name = "chef_michael"; Url = "https://images.unsplash.com/photo-1576267423048-15c0040fec78?q=80&w=600&auto=format&fit=crop" }
)

foreach ($chef in $chefs) {
    $outputPath = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images\$($chef.Name).jpg"
    Invoke-WebRequest -Uri $chef.Url -OutFile $outputPath
    Write-Host "Downloaded $($chef.Name).jpg"
}
