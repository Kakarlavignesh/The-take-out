$Images = @{
    "ambience_storefront.jpg"    = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=800&q=80"
    "ambience_neon_vibes.jpg"    = "https://images.pexels.com/photos/2290753/pexels-photo-2290753.jpeg?auto=compress&cs=tinysrgb&w=800" # Fallback/Alternative
    "ambience_interior_cozy.jpg" = "https://images.unsplash.com/photo-1559339352-11d035aa65de?auto=format&fit=crop&w=800&q=80" # Reusing lights as cozy if unique fails or finding fresh
    "ambience_bar.jpg"           = "https://images.pexels.com/photos/941861/pexels-photo-941861.jpeg?auto=compress&cs=tinysrgb&w=800"
    "ambience_lights.jpg"        = "https://images.unsplash.com/photo-1550966871-3ed3c622171c?auto=format&fit=crop&w=800&q=80" # Swapped
    "ambience_detail.jpg"        = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80"
}

$DestDir = "C:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images"

Write-Host "Downloading Premium Ambience Images..." -ForegroundColor Cyan

foreach ($Name in $Images.Keys) {
    $Url = $Images[$Name]
    $OutFile = Join-Path $DestDir $Name
    
    Write-Host "Downloading $Name..." -NoNewline
    try {
        # Using curl (via alias) logic or WebRequest with UserAgent
        $Request = [System.Net.WebRequest]::Create($Url)
        $Request.UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
        $Response = $Request.GetResponse()
        $Stream = $Response.GetResponseStream()
        $FileStream = [System.IO.File]::Create($OutFile)
        $Stream.CopyTo($FileStream)
        $FileStream.Close()
        $Response.Close()
        
        Write-Host " [OK]" -ForegroundColor Green
    }
    catch {
        Write-Host " [FAILED]" -ForegroundColor Red
        Write-Host $_.Exception.Message
        
        # Emergency Fallback to LoremFlickr if primary fails
        Write-Host "   -> Attempting fallback..." -ForegroundColor Yellow
        try {
            $FallbackUrl = "https://loremflickr.com/800/600/restaurant,luxury"
            Invoke-WebRequest -Uri $FallbackUrl -OutFile $OutFile -UserAgent "Mozilla/5.0"
            Write-Host "   [FALLBACK OK]" -ForegroundColor Green
        }
        catch {
            Write-Host "   [FALLBACK FAILED]" -ForegroundColor Red
        }
    }
}

Write-Host "Done!" -ForegroundColor Cyan
