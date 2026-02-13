$imageUrl = "https://images.unsplash.com/photo-1583394293214-28ded15ee548?q=80&w=800&auto=format&fit=crop"
$outputPath = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images\chef_marcus.jpg"

Invoke-WebRequest -Uri $imageUrl -OutFile $outputPath
Write-Host "Downloaded chef_marcus.jpg to $outputPath"
