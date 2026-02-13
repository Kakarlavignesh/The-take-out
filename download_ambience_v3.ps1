
# Force TLS 1.2 for secure connection
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$imgDir = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images"
New-Item -ItemType Directory -Force -Path $imgDir | Out-Null

function Download-Image {
    param ($url, $filename)
    try {
        Write-Host "Downloading $filename from $url..."
        # Add random parameter to prevent caching and get distinct images
        $finalUrl = "$url?random=$(Get-Random)"
        Invoke-WebRequest -Uri $finalUrl -OutFile (Join-Path $imgDir $filename) -UserAgent "Mozilla/5.0"
        Write-Host "Saved to $filename"
    }
    catch {
        Write-Warning "Failed to download $filename : $_"
    }
}

# 1. Storefront Exterior (Night)
Download-Image -url "https://loremflickr.com/1280/800/restaurant,exterior,night" -filename "ambience_new_storefront_1.jpg"

# 2. Outdoor Patio
Download-Image -url "https://loremflickr.com/1280/800/cafe,patio" -filename "ambience_new_outdoor.jpg"

# 3. Interior Booths
Download-Image -url "https://loremflickr.com/1280/800/restaurant,interior,booth" -filename "ambience_new_booths.jpg"

# 4. Service Counter
Download-Image -url "https://loremflickr.com/1280/800/coffee,shop,counter" -filename "ambience_new_counter.jpg"

# 5. Community Table
Download-Image -url "https://loremflickr.com/1280/800/dining,table,wood" -filename "ambience_new_community_table.jpg"

# 6. Kitchen Staff/Chef
Download-Image -url "https://loremflickr.com/1280/800/chef,cooking,kitchen" -filename "ambience_new_kitchen.jpg"

# 7. Burger Pattern
Download-Image -url "https://loremflickr.com/1280/800/burger,fries" -filename "ambience_new_burger.jpg"

# 8. Takeout Bag
Download-Image -url "https://loremflickr.com/1280/800/paper,bag,food" -filename "ambience_new_takeout.jpg"

# 9. Cozy Corner
Download-Image -url "https://loremflickr.com/1280/800/cozy,restaurant,corner" -filename "ambience_new_storefront_2.jpg"

Write-Host "Ambience Correction Complete."
