
$imgDir = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images"

function Download-Unsplash {
    param ($query, $filename)
    try {
        $url = "https://source.unsplash.com/800x600/?$query"
        # Since unsplash source is deprecated/redirects, let's use direct impactful random images or better queries
        # Actually using source.unsplash often redirects. Better to use specific IDs if possible, or reliable keywords.
        # Let's try to use pixel-perfect random queries which usually work better.
        $url = "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?auto=format&fit=crop&w=800&q=80" # Samosa/Fried
        
        if ($query -eq "french-fries") { $url = "https://images.unsplash.com/photo-1630384060421-cb20d0e0649e?auto=format&fit=crop&w=800&q=80" } # Retry previous or generic
        if ($query -eq "popcorn") { $url = "https://images.unsplash.com/photo-1578849278619-e73505e9610f?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "burger") { $url = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "sandwich") { $url = "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "milkshake") { $url = "https://images.unsplash.com/photo-1579954115545-a95591f28bfc?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "brownie") { $url = "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "pani-puri") { $url = "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "vada-pav") { $url = "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "pakora") { $url = "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "tikka") { $url = "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "cutlet") { $url = "https://images.unsplash.com/photo-1565557623262-b51c2513a641?auto=format&fit=crop&w=800&q=80" } 
        if ($query -eq "bhel-puri") { $url = "https://images.unsplash.com/photo-1626132647523-66f5bf380027?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "makhana") { $url = "https://images.unsplash.com/photo-1578849278619-e73505e9610f?auto=format&fit=crop&w=800&q=80" } # Use popcorn like image for makhana
        if ($query -eq "spring-rolls") { $url = "https://images.unsplash.com/photo-1544025162-d76694265947?auto=format&fit=crop&w=800&q=80" }
        if ($query -eq "chips") { $url = "https://images.unsplash.com/photo-1566478989037-eec170784d0b?auto=format&fit=crop&w=800&q=80" } # Banana Chips / Chips

        Write-Host "Downloading $filename..."
        Invoke-WebRequest -Uri $url -OutFile (Join-Path $imgDir $filename)
        Write-Host "Saved $filename"
    }
    catch {
        Write-Warning "Failed to download ${filename}: $_"
    }
}

Download-Unsplash "french-fries" "snacks_fries.jpg"
Download-Unsplash "popcorn" "snacks_popcorn.jpg"
Download-Unsplash "burger" "snacks_burger.jpg" # Update existing
Download-Unsplash "sandwich" "snacks_sandwich.jpg"
Download-Unsplash "milkshake" "snacks_milkshake.jpg"
Download-Unsplash "brownie" "snacks_brownie.jpg"
Download-Unsplash "pani-puri" "snacks_pani_puri.jpg"
Download-Unsplash "vada-pav" "snacks_vada_pav.jpg"
Download-Unsplash "pakora" "snacks_pakora.jpg"
Download-Unsplash "tikka" "snacks_tikka.jpg" # Fix broken
Download-Unsplash "cutlet" "snacks_cutlet.jpg"
Download-Unsplash "bhel-puri" "snacks_bhel_puri.jpg"
Download-Unsplash "spring-rolls" "snacks_rolls.jpg" # Update existing
Download-Unsplash "chips" "snacks_chips.jpg"
Download-Unsplash "makhana" "snacks_makhana.jpg"
