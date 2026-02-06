
$imgDir = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images"

function Download-FoodishImage {
    param ($category, $filename)
    try {
        $apiUri = "https://foodish-api.com/api/images/$category"
        $response = Invoke-RestMethod -Uri $apiUri -ErrorAction Stop
        if ($response.image) {
            Write-Host "Downloading $category image to $filename..."
            Invoke-WebRequest -Uri $response.image -OutFile (Join-Path $imgDir $filename)
            Write-Host "Success."
        }
    }
    catch {
        Write-Warning "Failed to download $filename : $_"
    }
}

# Fix missing Breakfast items
Download-FoodishImage -category "rice" -filename "breakfast.jpg"          # Fallback
Download-FoodishImage -category "rice" -filename "breakfast_idli.jpg"     # Fallback
Download-FoodishImage -category "pizza" -filename "breakfast_omelette.jpg" # Fallback (generic food)
Download-FoodishImage -category "dessert" -filename "breakfast_pancakes.jpg"

Write-Host "Fix complete."
