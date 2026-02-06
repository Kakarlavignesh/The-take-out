
$imgDir = "c:\Users\asus\OneDrive\Desktop\The Takeout\server\src\main\resources\static\images"
New-Item -ItemType Directory -Force -Path $imgDir

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
        Write-Warning "Failed to download $filename from category $category : $_"
    }
}

function Download-DirectImage {
    param ($url, $filename)
    try {
        Write-Host "Downloading direct image to $filename..."
        Invoke-WebRequest -Uri $url -OutFile (Join-Path $imgDir $filename)
        Write-Host "Success."
    }
    catch {
        Write-Warning "Failed to download $filename from URL : $_"
    }
}

# Breakfast
Download-FoodishImage -category "idli" -filename "breakfast.jpg"
Download-FoodishImage -category "idli" -filename "breakfast_idli.jpg"
Download-FoodishImage -category "dosa" -filename "breakfast_dosa.jpg"
Download-FoodishImage -category "rice" -filename "breakfast_poha.jpg" # Poha approx
Download-DirectImage -url "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Pancakes_%28Unsplash_CKipn7XSyEU%29.jpg/800px-Pancakes_%28Unsplash_CKipn7XSyEU%29.jpg" -filename "breakfast_pancakes.jpg"
# Omelette URL from Wikimedia or similar
Download-DirectImage -url "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Omelette_garnie.jpg/800px-Omelette_garnie.jpg" -filename "breakfast_omelette.jpg" 
Download-FoodishImage -category "samosa" -filename "breakfast_paratha.jpg" # Approx
Download-FoodishImage -category "dessert" -filename "breakfast_smoothie.jpg"
Download-FoodishImage -category "burger" -filename "breakfast_sandwich.jpg"
Download-FoodishImage -category "dessert" -filename "breakfast_fruit.jpg"
Download-FoodishImage -category "burger" -filename "breakfast_toast.jpg"

# Lunch (Generic + Specifics)
Download-FoodishImage -category "biryani" -filename "lunch.jpg"
Download-FoodishImage -category "biryani" -filename "lunch_biryani.jpg" # Thali/Biryani
Download-FoodishImage -category "butter-chicken" -filename "lunch_paneer.jpg"
Download-FoodishImage -category "butter-chicken" -filename "lunch_curry.jpg"
Download-FoodishImage -category "rice" -filename "lunch_rice.jpg"

# Snacks
Download-FoodishImage -category "samosa" -filename "snacks.jpg"
Download-FoodishImage -category "samosa" -filename "snacks_samosa.jpg"
Download-FoodishImage -category "burger" -filename "snacks_burger.jpg"
Download-FoodishImage -category "dessert" -filename "snacks_dessert.jpg"

# Dinner
Download-FoodishImage -category "pizza" -filename "dinner.jpg"
Download-FoodishImage -category "pizza" -filename "dinner_pizza.jpg"
Download-FoodishImage -category "pasta" -filename "dinner_pasta.jpg"
Download-FoodishImage -category "burger" -filename "dinner_burger.jpg"
Download-FoodishImage -category "butter-chicken" -filename "dinner_curry.jpg"

Write-Host "Download complete."
