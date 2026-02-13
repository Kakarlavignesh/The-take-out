
# Force TLS 1.2
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$imgDir = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images"
New-Item -ItemType Directory -Force -Path $imgDir | Out-Null

function Download-Image {
    param ($url, $filename)
    try {
        Write-Host "Downloading $filename..."
        Invoke-WebRequest -Uri $url -OutFile (Join-Path $imgDir $filename) -UserAgent "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
        Write-Host "Saved to $filename"
    }
    catch {
        Write-Warning "Failed to download $filename : $_"
    }
}

# Verified Pexels IDs for Restaurant Theme
# 1. Storefront (Night/Evening vibe)
Download-Image -url "https://images.pexels.com/photos/1267320/pexels-photo-1267320.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_storefront_1.jpg"

# 2. Outdoor Patio
Download-Image -url "https://images.pexels.com/photos/67468/pexels-photo-67468.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_outdoor.jpg"

# 3. Interior Booths
Download-Image -url "https://images.pexels.com/photos/262978/pexels-photo-262978.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_booths.jpg"

# 4. Service Counter / Bar
Download-Image -url "https://images.pexels.com/photos/262918/pexels-photo-262918.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_counter.jpg"

# 5. Community Table
Download-Image -url "https://images.pexels.com/photos/2079438/pexels-photo-2079438.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_community_table.jpg"

# 6. Kitchen Staff / Chef
Download-Image -url "https://images.pexels.com/photos/4253302/pexels-photo-4253302.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_kitchen.jpg"

# 7. Premium Burger
Download-Image -url "https://images.pexels.com/photos/1639562/pexels-photo-1639562.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_burger.jpg"

# 8. Takeout / Food Detail
Download-Image -url "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_takeout.jpg"

# 9. Cozy Corner / Mood
Download-Image -url "https://images.pexels.com/photos/260922/pexels-photo-260922.jpeg?auto=compress&cs=tinysrgb&h=800" -filename "ambience_new_storefront_2.jpg"

Write-Host "Pexels restaurant image download complete."
