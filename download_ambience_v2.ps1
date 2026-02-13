
# Force TLS 1.2
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$imgDir = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images"
New-Item -ItemType Directory -Force -Path $imgDir | Out-Null

function Download-Image {
    param ($url, $filename)
    try {
        Write-Host "Downloading $filename from $url..."
        Invoke-WebRequest -Uri $url -OutFile (Join-Path $imgDir $filename) -UserAgent "Mozilla/5.0"
        Write-Host "Saved to $filename"
    }
    catch {
        Write-Warning "Failed to download $filename : $_"
        # Fallback only if file doesn't exist
        if (-not (Test-Path (Join-Path $imgDir $filename))) {
            $fallback = Join-Path $imgDir "hero_bg.jpg"
            if (Test-Path $fallback) {
                Copy-Item -Path $fallback -Destination (Join-Path $imgDir $filename) -Force
                Write-Warning "Used fallback hero_bg.jpg for $filename"
            }
        }
    }
}

# 1. Storefront Exterior
Download-Image -url "https://picsum.photos/seed/storefront/1280/800" -filename "ambience_new_storefront_1.jpg"

# 2. Outdoor Patio Seating
Download-Image -url "https://picsum.photos/seed/patio/1280/800" -filename "ambience_new_outdoor.jpg"

# 3. Interior Booths
Download-Image -url "https://picsum.photos/seed/booths/1280/800" -filename "ambience_new_booths.jpg"

# 4. Service Counter
Download-Image -url "https://picsum.photos/seed/counter/1280/800" -filename "ambience_new_counter.jpg"

# 5. Long Community Table
Download-Image -url "https://picsum.photos/seed/table/1280/800" -filename "ambience_new_community_table.jpg"

# 6. Kitchen Staff
Download-Image -url "https://picsum.photos/seed/chef/1280/800" -filename "ambience_new_kitchen.jpg"

# 7. Burger Spread
Download-Image -url "https://picsum.photos/seed/burger/1280/800" -filename "ambience_new_burger.jpg"

# 8. Takeout Bag
Download-Image -url "https://picsum.photos/seed/bag/1280/800" -filename "ambience_new_takeout.jpg"

# 9. Cozy Corner
Download-Image -url "https://picsum.photos/seed/corner/1280/800" -filename "ambience_new_storefront_2.jpg"

Write-Host "Download with TLS 1.2 complete."
