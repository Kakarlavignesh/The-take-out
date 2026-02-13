// Base URL for API (Absolute path to ensure file:// access works)
const API_BASE = 'http://localhost:9091/api';

// Utility: Show/Hide Elements
function show(id) { document.getElementById(id).style.display = 'block'; }
function hide(id) { document.getElementById(id).style.display = 'none'; }

// --- THEME LOGIC ---
function toggleTheme() {
    const current = localStorage.getItem('theme') || 'dark';
    const next = current === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-theme', next);
    localStorage.setItem('theme', next);
}

// Init Theme
(function initTheme() {
    const saved = localStorage.getItem('theme') || 'dark';
    document.documentElement.setAttribute('data-theme', saved);
})();

// --- REFINED LOGIN LOGIC ---

// --- SECURE BACKEND LOGIN LOGIC ---

// --- OTP & GOOGLE LOGIN SIMULATION (User Experience Focus) ---

// Store mock OTP
let activeOTP = null;
let activeMobile = null;

// Simulate Phone Notification (to show OTP "delivery")
function showPhoneNotification(message) {
    const notif = document.createElement('div');
    notif.className = 'phone-notification';
    notif.innerHTML = `
        <div style="display:flex; align-items:center; gap:10px;">
            <div style="background:#25D366; width:30px; height:30px; border-radius:50%; display:flex; justify-content:center; align-items:center; color:white; font-weight:bold;">T</div>
            <div>
                <strong style="display:block; font-size:0.8rem; color:#333;">Messages • Now</strong>
                <span style="font-size:0.9rem; color:#000;">${message}</span>
            </div>
        </div>
    `;

    // Style the notification
    Object.assign(notif.style, {
        position: 'fixed',
        top: '20px',
        left: '50%',
        transform: 'translateX(-50%) translateY(-100px)',
        background: '#fff',
        padding: '15px 25px',
        borderRadius: '50px',
        boxShadow: '0 10px 30px rgba(0,0,0,0.5)',
        zIndex: '100000',
        minWidth: '320px',
        border: '2px solid #25D366',
        transition: 'transform 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275)',
        color: '#000'
    });

    document.body.appendChild(notif);

    // Animate In
    setTimeout(() => notif.style.transform = 'translateX(-50%) translateY(0)', 100);

    // Animate Out
    setTimeout(() => {
        notif.style.transform = 'translateX(-50%) translateY(-150px)';
        setTimeout(() => notif.remove(), 500);
    }, 8000); // Show for 8 seconds
}

async function sendLoginOtp() {
    const mobile = document.getElementById('user-mobile').value;
    if (!mobile || mobile.length < 10) return alert("Please enter a valid 10-digit mobile number");

    const btn = document.getElementById('send-otp-btn');
    const originalText = btn.innerText;
    btn.innerText = "Sending...";
    btn.disabled = true;

    try {
        console.log(`📡 Requesting OTP for ${mobile}...`);
        const res = await fetch(`${API_BASE}/auth/send-otp`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ mobile: mobile })
        });

        if (res.ok) {
            const data = await res.json();
            const otp = data.otp; // Received from our modified backend
            activeOTP = otp.toString();
            activeMobile = mobile;

            // Show VERY prominent "Phone Notification"
            showPhoneNotification(`Your verification code is: <strong style="font-size:1.2rem; color:#25D366;">${otp}</strong>`);

            // Update UI
            btn.style.display = 'none';
            document.getElementById('otp-section').style.display = 'block';
            document.getElementById('login-btn').style.display = 'block';

            // Helpful hint on the input itself
            const otpInput = document.getElementById('user-otp');
            if (otpInput) {
                otpInput.placeholder = `Enter ${otp}`;
                otpInput.focus();
            }

            console.log(`✅ OTP Saved: ${activeOTP}`);
        } else {
            alert("Failed to send OTP. Server error.");
            btn.innerText = originalText;
            btn.disabled = false;
        }
    } catch (err) {
        console.error("OTP Error:", err);
        alert("Connection Failed. Is the server running?");
        btn.innerText = originalText;
        btn.disabled = false;
    }
}

async function handleUserLogin(e) {
    e.preventDefault();
    const enteredLink = document.getElementById('user-otp').value;

    // Use activeOTP from previous sendOtp call (which comes from backend simulation)
    // Note: In real world, verify OTP via backend call.
    // For now, let's assume activeOTP is valid since it was logged by backend.

    // BETTER: Call backend to verify OTP and get user
    try {
        const res = await fetch(`${API_BASE}/auth/customer-login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ mobile: activeMobile, otp: enteredLink })
        });

        if (res.ok) {
            const user = await res.json();
            localStorage.setItem('user', JSON.stringify(user));
            localStorage.removeItem('staff');
            alert("Login Successful! Welcome.");
            window.location.reload();
        } else {
            alert("Invalid OTP! Please check the notification.");
        }
    } catch (err) {
        console.error("Login failed", err);
        alert("Login failed. Is server running?");
    }
}

// Google Login Handler
async function handleGoogleLogin() {
    const btn = document.querySelector('button[onclick="handleGoogleLogin()"]');
    const originalText = btn.innerText;
    btn.innerText = "Connecting to Google...";
    btn.disabled = true;

    try {
        // Simulate getting Google token, then call our backend
        // In real app, Google OAuth flow happens here.
        setTimeout(async () => {
            const res = await fetch(`${API_BASE}/auth/google-login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    email: 'user@gmail.com',
                    name: 'Google User'
                })
            });

            if (res.ok) {
                const user = await res.json();
                localStorage.setItem('user', JSON.stringify(user));
                localStorage.removeItem('staff');
                alert("Successfully logged in with Google!");
                window.location.reload();
            } else {
                alert("Google Login Failed on Server.");
                btn.innerText = originalText;
                btn.disabled = false;
            }
        }, 1500);
    } catch (err) {
        console.error("Google Login Error", err);
        alert("Error connecting to server.");
        btn.innerText = originalText;
        btn.disabled = false;
    }
}

// Global Auth Handler for Navbar
window.handleAuthClick = function () {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user) {
        // Logout Logic
        if (confirm("Are you sure you want to logout?")) {
            localStorage.removeItem('user');
            localStorage.removeItem('staff');
            alert("Logged Out Successfully");
            updateAuthUI();
            window.location.reload();
        }
    } else {
        // Open Login Modal
        const modal = document.getElementById('login-modal');
        if (modal) {
            modal.style.display = 'flex';
            if (typeof switchTab === 'function') switchTab('user'); // Default to user tab
        }
    }
};

// Update UI based on Auth State
function updateAuthUI() {
    const authBtn = document.getElementById('nav-login-btn');
    const user = JSON.parse(localStorage.getItem('user'));

    if (authBtn) {
        if (user) {
            authBtn.innerText = "Logout";
            authBtn.style.color = "#ff4d4d"; // Red for logout
        } else {
            authBtn.innerText = "Login";
            authBtn.style.color = ""; // Reset
        }
    }
}

// Run on load
document.addEventListener('DOMContentLoaded', updateAuthUI);

// Tab Switching for Index Modal
// Tab Switching for Index Modal
window.switchTab = function (type) {
    document.querySelectorAll('.auth-tab-btn').forEach(b => b.classList.remove('active'));
    document.getElementById('user-login').style.display = 'none';
    document.getElementById('staff-login').style.display = 'none';
    document.getElementById('admin-login').style.display = 'none';

    if (type === 'user') {
        document.getElementById('user-login').style.display = 'block';
        document.querySelector('.auth-tabs button:nth-child(1)').classList.add('active');
    } else if (type === 'staff') {
        document.getElementById('staff-login').style.display = 'block';
        document.querySelector('.auth-tabs button:nth-child(2)').classList.add('active');
    } else if (type === 'admin') {
        document.getElementById('admin-login').style.display = 'block';
        document.querySelector('.auth-tabs button:nth-child(3)').classList.add('active');
    }
};

// Premium Toast Notification
function showToast(message, type = 'success') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        container.style.cssText = "position: fixed; top: 20px; right: 20px; z-index: 10000; display: flex; flex-direction: column; gap: 10px;";
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    const borderColor = type === 'error' ? '#ff4d4d' : '#00ff88';
    const icon = type === 'error' ? '⚠️' : '✅';

    toast.style.cssText = `
        background: rgba(10, 10, 15, 0.95);
        color: white;
        padding: 16px 24px;
        border-radius: 12px;
        border-right: 4px solid ${borderColor};
        box-shadow: 0 10px 30px rgba(0,0,0,0.5);
        display: flex;
        align-items: center;
        gap: 15px;
        min-width: 300px;
        transform: translateX(100%);
        transition: transform 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
        backdrop-filter: blur(10px);
        font-family: 'Lato', sans-serif;
    `;

    toast.innerHTML = `
        <span style="font-size: 1.5rem;">${icon}</span>
        <div>
            <h4 style="margin:0; font-size: 1rem; color: ${borderColor}; text-transform:uppercase; letter-spacing:1px;">${type}</h4>
            <p style="margin:5px 0 0; font-size: 0.9rem; opacity: 0.8;">${message}</p>
        </div>
    `;

    container.appendChild(toast); // Add to container

    // Animate In
    requestAnimationFrame(() => {
        toast.style.transform = 'translateX(0)';
    });

    // Remove after 3s
    setTimeout(() => {
        toast.style.transform = 'translateX(120%)';
        setTimeout(() => toast.remove(), 400);
    }, 3000);
}

async function handleStaffLogin(e) {
    e.preventDefault();
    console.log("-----------------------------------------");
    console.log("🚀 Login Process Initiated");

    const isStaffForm = e.target.id === 'staff-form' || e.target.id === 'staff-login';

    // Select inputs robustly
    const idField = isStaffForm ? (document.getElementById('staff-id') || document.getElementById('emp-id')) : document.getElementById('admin-id');
    const passField = isStaffForm ? (document.getElementById('staff-pass') || document.getElementById('emp-pass')) : document.getElementById('admin-pass');

    const id = idField ? idField.value.trim() : "";
    const pass = passField ? passField.value : "";

    console.log(`👤 User Type: ${isStaffForm ? 'Staff' : 'Admin'}`);
    console.log(`🔑 ID Provided: ${id}`);
    console.log(`🔒 Password Length: ${pass.length}`);

    if (!id || !pass) {
        console.warn("⚠️ Missing Credentials");
        showToast("Please enter both ID and Password", "error");
        return;
    }

    try {
        console.log("📡 Sending Request to /api/auth/staff-login...");

        const res = await fetch(`${API_BASE}/auth/staff-login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id, password: pass })
        });

        console.log(`📥 Response Status: ${res.status}`);

        if (res.ok) {
            const data = await res.json();
            // Handle both structure types (User object only OR {user, token})
            const emp = data.user || data;
            const token = data.token || "mock-jwt-token";

            console.log("✅ Login Successful", emp);
            console.log("🎟️ Token Received:", token);

            localStorage.setItem('staff', JSON.stringify(emp));
            localStorage.setItem('authToken', token);
            localStorage.removeItem('user');

            showToast(`Welcome back, ${emp.name}`, "success");

            // Redirect Logic
            setTimeout(() => {
                if (emp.role === 'Admin' || emp.role === 'Head' || emp.department === 'Management') {
                    console.log("➡️ Redirecting to Dashboard (Admin)");
                    window.location.href = 'admin.html';
                } else {
                    console.log("➡️ Redirecting to Orders (Staff)");
                    window.location.href = 'orders.html';
                }
            }, 1500); // 1.5s delay for toast to be seen

        } else {
            console.warn("❌ Login Failed: Invalid Credentials");
            showToast("Invalid ID or Password", "error");
        }
    } catch (e) {
        console.error("🔥 Network/Server Error:", e);
        showToast("Server Connection Failed", "error");
    }
}

function logout() {
    localStorage.clear();
    location.href = 'login.html';
}

// Staff Signup
// Staff Signup removed as per requirements


// Menu Logic
let currentModalItem = null;
let currentModalQty = 1;
let cart = [];

// Initialize removed (consolidated below)

async function loadMenu(category = 'ALL') {
    const container = document.getElementById('menu-container');
    container.innerHTML = '<p style="text-align:center;">Loading exquisite dishes...</p>';

    // Update buttons
    // Update buttons
    document.querySelectorAll('.filter-btn').forEach(btn => {
        // Compare the onClick argument or a data attribute instead of InnerText
        const btnFn = btn.getAttribute('onclick');
        if (btnFn && btnFn.includes(`'${category}'`)) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });

    try {
        // Explicitly pointing to port 9091
        const res = await fetch('http://localhost:9091/api/menu');
        if (!res.ok) throw new Error('Failed to fetch');
        let data = await res.json();

        if (category !== 'ALL') {
            data = data.filter(item => item.category === category);
        }

        container.innerHTML = '';
        if (data.length === 0) {
            container.innerHTML = '<p style="text-align:center;">No items found in this category.</p>';
            return;
        }

        data.forEach(item => {
            const card = document.createElement('div');
            card.className = 'menu-item';
            card.onclick = (e) => {
                if (!e.target.closest('.add-btn-small')) openDishModal(item);
            };

            // Generate star rating randomly for UI effect
            const stars = "★".repeat(4 + Math.round(Math.random()));

            card.innerHTML = `
                <img src="${item.imageUrl || 'https://via.placeholder.com/300'}" alt="${item.name}">
                <div style="display:flex; justify-content:space-between; align-items:flex-start;">
                    <h3>${item.name}</h3>
                    <span style="color:gold;">${stars}</span>
                </div>
                <p>${item.description}</p>
                <div class="price-tag">
                    <span>₹${item.price}</span>
                    <button class="add-btn-small" onclick="addToCart(${item.id}, 1, '${item.name}', ${item.price})">＋</button>
                </div>
            `;
            container.appendChild(card);
        });
    } catch (err) {
        console.error(err);
        container.innerHTML = '<p style="text-align:center; color:red;">Failed to load menu. Is server running?</p>';
    }
}

// Modal Logic
function openDishModal(item) {
    currentModalItem = item;
    currentModalQty = 1;

    document.getElementById('modal-img').src = item.imageUrl || 'https://via.placeholder.com/300';
    document.getElementById('modal-title').innerText = item.name;
    document.getElementById('modal-desc').innerText = item.description;
    document.getElementById('modal-ingredients').innerText = item.ingredients || "Secret Chef's Blend";
    document.getElementById('qty-display').innerText = currentModalQty;
    updateModalPrice();

    document.getElementById('dish-modal').style.display = 'flex';
}

function updateModalQty(change) {
    if (currentModalQty + change >= 1) {
        currentModalQty += change;
        document.getElementById('qty-display').innerText = currentModalQty;
        updateModalPrice();
    }
}

function updateModalPrice() {
    if (currentModalItem) {
        const total = (currentModalItem.price * currentModalQty).toFixed(2);
        document.getElementById('modal-price').innerText = total;
    }
}

function addToCartFromModal() {
    if (currentModalItem) {
        addToCart(currentModalItem.id, currentModalQty, currentModalItem.name, currentModalItem.price);
        closeModal('dish-modal');
    }
}

function closeModal(id) {
    document.getElementById(id).style.display = 'none';
}

// Cart Logic
function addToCart(id, qty, name, price) {
    const existing = cart.find(i => i.id === id);
    if (existing) {
        existing.qty += qty;
    } else {
        cart.push({ id, qty, name, price });
    }
    updateCartCount();

    // Visual feedback
    const btn = document.querySelector('.cart-float');
    btn.style.transform = 'scale(1.3)';
    setTimeout(() => btn.style.transform = 'scale(1)', 200);
}

function updateCartCount() {
    const count = cart.reduce((acc, item) => acc + item.qty, 0);
    const badge = document.getElementById('cart-count');
    if (badge) badge.innerText = count;
}

function openCart() {
    renderCartItems();
    document.getElementById('cart-modal').style.display = 'flex';
}

function renderCartItems() {
    const list = document.getElementById('cart-items');
    list.innerHTML = '';
    let total = 0;

    if (cart.length === 0) {
        list.innerHTML = '<p style="text-align:center; color:#888; padding: 2rem;">Your cart is empty.</p>';
    } else {
        cart.forEach((item, index) => {
            const itemTotal = item.price * item.qty;
            total += itemTotal;

            const div = document.createElement('div');
            div.className = 'cart-item';
            div.innerHTML = `
                <div class="cart-item-details">
                    <div class="cart-item-info">${item.name}</div>
                    <div class="cart-item-price">₹${item.price} x ${item.qty}</div>
                </div>
                <div style="display:flex; align-items:center; gap:15px;">
                    <span class="cart-item-total">₹${itemTotal.toFixed(2)}</span>
                    <button onclick="removeFromCart(${index})" class="btn-remove" title="Remove Item">&times;</button>
                </div>
            `;
            list.appendChild(div);
        });
    }

    // Update Total Display with new class
    const totalEl = document.getElementById('cart-total');
    if (totalEl) {
        // Find parent to style if needed, but we mostly just need to style the span or container
        // The HTML in menu.html needs to match. Let's update menu.html next to clean up the container buttons too.
        totalEl.innerHTML = `<span class="cart-total-amount">${total.toFixed(2)}</span>`;
    }
}

function removeFromCart(index) {
    cart.splice(index, 1);
    renderCartItems();
    updateCartCount();
}

function clearCart() {
    cart = [];
    renderCartItems();
    updateCartCount();
}

// Billing Logic
function generateBill() {
    if (cart.length === 0) return;

    // Populate Bill Section
    const date = new Date().toLocaleDateString();
    const orderId = Math.floor(Math.random() * 900000) + 100000;

    document.getElementById('bill-date').innerText = date;
    document.getElementById('bill-id').innerText = orderId;

    const tbody = document.getElementById('bill-items');
    tbody.innerHTML = '';
    let subTotal = 0;

    // Use a simple number to words function (simplified for demo)
    const numberToWords = (num) => {
        // Very basic implementation or placeholder
        return "Rupees " + num + " Only";
        // Note: For a full solution, we'd need a robust library or larger function.
    };

    cart.forEach((item, index) => {
        const total = item.price * item.qty;
        subTotal += total;

        tbody.innerHTML += `
            <tr>
                <td>${(index + 1).toString().padStart(2, '0')}</td>
                <td>${item.name}</td>
                <td>₹${item.price.toFixed(0)}</td>
                <td>${item.qty}</td>
                <td>₹${total.toFixed(0)}</td>
            </tr>
        `;
    });

    // Calculate Tax and Total
    const tax = subTotal * 0.05;
    const grandTotal = subTotal + tax;

    document.getElementById('bill-subtotal').innerText = subTotal.toFixed(2);
    document.getElementById('bill-tax').innerText = tax.toFixed(2);
    document.getElementById('bill-total').innerText = grandTotal.toFixed(2);

    // Update Amount in Words
    document.getElementById('amount-words').innerText = convertNumberToWords(Math.round(grandTotal));

    // GENERATE STATIC UPI QR CODE (Strict NPCI Format)
    // Format: upi://pay?pa=<id>&pn=<name>&mc=0000&mode=02&purpose=00
    const userUpiId = "9393737395@ybl";
    const merchantName = "TheTakeout";

    const upiLink = `upi://pay?pa=${userUpiId}&pn=${merchantName}&mc=0000&mode=02&purpose=00`;
    const qrUrl = `https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(upiLink)}`;

    const qrImg = document.getElementById('invoice-qr');
    if (qrImg) {
        qrImg.src = "images/static-qr.png";
    }

    // Close modal and print
    closeModal('cart-modal');
    setTimeout(() => {
        window.print();
        // Redirect to orders page after printing
        window.location.href = 'orders.html';
    }, 1000); // Increased timeout slightly for QR to load
}

// Helper: Number to Words (Indian Currency)
function convertNumberToWords(amount) {
    const words = new Array();
    words[0] = '';
    words[1] = 'One';
    words[2] = 'Two';
    words[3] = 'Three';
    words[4] = 'Four';
    words[5] = 'Five';
    words[6] = 'Six';
    words[7] = 'Seven';
    words[8] = 'Eight';
    words[9] = 'Nine';
    words[10] = 'Ten';
    words[11] = 'Eleven';
    words[12] = 'Twelve';
    words[13] = 'Thirteen';
    words[14] = 'Fourteen';
    words[15] = 'Fifteen';
    words[16] = 'Sixteen';
    words[17] = 'Seventeen';
    words[18] = 'Eighteen';
    words[19] = 'Nineteen';
    words[20] = 'Twenty';
    words[30] = 'Thirty';
    words[40] = 'Forty';
    words[50] = 'Fifty';
    words[60] = 'Sixty';
    words[70] = 'Seventy';
    words[80] = 'Eighty';
    words[90] = 'Ninety';

    amount = amount.toString();
    const atemp = amount.split(".");
    let number = atemp[0].split(",").join("");
    const n_length = number.length;
    let words_string = "";

    if (n_length <= 9) {
        const n_array = new Array(0, 0, 0, 0, 0, 0, 0, 0, 0);
        const received_n_array = new Array();
        for (let i = 0; i < n_length; i++) {
            received_n_array[i] = number.substr(i, 1);
        }
        for (let i = 9 - n_length, j = 0; i < 9; i++, j++) {
            n_array[i] = received_n_array[j];
        }
        for (let i = 0, j = 1; i < 9; i++, j++) {
            if (i == 0 || i == 2 || i == 4 || i == 7) {
                if (n_array[i] == 1) {
                    n_array[j] = 10 + parseInt(n_array[j]);
                    n_array[i] = 0;
                }
            }
        }
        let value = "";
        for (let i = 0; i < 9; i++) {
            if (i == 0 || i == 2 || i == 4 || i == 7) {
                value = n_array[i] * 10;
            } else {
                value = n_array[i];
            }
            if (value != 0) {
                words_string += words[value] + " ";
            }
            if ((i == 1 && value != 0) || (i == 0 && value != 0 && n_array[i + 1] == 0)) {
                words_string += "Crores ";
            }
            if ((i == 3 && value != 0) || (i == 2 && value != 0 && n_array[i + 1] == 0)) {
                words_string += "Lakhs ";
            }
            if ((i == 5 && value != 0) || (i == 4 && value != 0 && n_array[i + 1] == 0)) {
                words_string += "Thousand ";
            }
            if (i == 6 && value != 0 && (n_array[i + 1] != 0 && n_array[i + 2] != 0)) {
                words_string += "Hundred and ";
            } else if (i == 6 && value != 0) {
                words_string += "Hundred ";
            }
        }
        words_string = words_string.split("  ").join(" ");
    }
    return words_string + " Rupees Only";
}

// Close modals on outside click
window.onclick = function (event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = "none";
    }
}

// Feedback Logic
async function submitFeedback(empId, rating, comment) {
    const res = await fetch(`${API_BASE}/feedback`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            employee: { id: empId },
            rating: parseFloat(rating),
            comment: comment
        })
    });
    if (res.ok) alert("Feedback Submitted! Thank you.");
}

// Initial Load
window.addEventListener('load', () => {
    // Check if on menu page
    if (document.getElementById('menu-container')) {
        loadMenu();
        updateCartCount();
    }
    // Check if on chef page
    if (document.getElementById('chef-orders')) loadChefOrders();
    // Check Wallet
    if (document.getElementById('wallet-balance')) loadWallet();
});

// Chef Logic
async function loadChefOrders() {
    const res = await fetch(`${API_BASE}/chef/orders`);
    const orders = await res.json();

    const container = document.getElementById('chef-orders');
    container.innerHTML = orders.map(o => `
        <div class="chef-card ${o.status.toLowerCase()}">
            <h4>Order #${o.id} <span class="badge">${o.status}</span></h4>
            <p><strong>Items:</strong> ${o.itemsSummary}</p>
            <p><strong>Time:</strong> <input type="number" value="${o.estimatedTimeMinutes || 0}" style="width:50px" onchange="updateOrder(${o.id}, 'time', this.value)"> min</p>
            <div class="actions">
                <button onclick="updateOrder(${o.id}, 'status', 'COOKING')">Start Cooking</button>
                <button onclick="updateOrder(${o.id}, 'status', 'READY')">Mark Ready</button>
                <button onclick="updateOrder(${o.id}, 'status', 'SERVED')">Served</button>
            </div>
        </div>
    `).join('');
}

async function updateOrder(id, field, value) {
    const payload = { orderId: id };
    if (field === 'status') {
        payload.status = value;
        payload.chefId = document.getElementById('current-chef-id').value; // Hidden input
    } else {
        payload.status = "COOKING"; // Default
        payload.time = value;
    }

    await fetch(`${API_BASE}/chef/update-status`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    loadChefOrders();
}

// Wallet Logic
async function loadWallet() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) return;

    const res = await fetch(`${API_BASE}/wallet/${user.id}`);
    if (res.ok) {
        const updatedUser = await res.json();
        document.getElementById('wallet-balance').innerText = updatedUser.walletBalance || 0;
        document.getElementById('qr-code').innerText = updatedUser.qrCodeString || 'Scan to Generate';
    }
}

// --- NEW PAYMENT MODAL LOGIC ---
function openPaymentModal() {
    if (cart.length === 0) return;

    // Calculate Total
    let total = 0;
    cart.forEach(item => total += item.price * item.qty);
    const tax = total * 0.05;
    const grandTotal = total + tax;

    // Update Modal UI
    document.getElementById('payment-total-amount').innerText = "₹" + grandTotal.toFixed(2);

    // GENERATE STATIC UPI QR CODE (Strict NPCI Format)
    // Format: upi://pay?pa=<id>&pn=<name>&mc=0000&mode=02&purpose=00
    const userUpiId = "9393737395@ybl";
    const merchantName = "TheTakeout";

    // Strict parameters for Static Merchant QR
    const upiLink = `upi://pay?pa=${userUpiId}&pn=${merchantName}&mc=0000&mode=02&purpose=00`;
    const qrUrl = `https://api.qrserver.com/v1/create-qr-code/?size=250x250&margin=0&data=${encodeURIComponent(upiLink)}`;

    const qrImg = document.getElementById('payment-qr-img');
    if (qrImg) qrImg.src = "images/static-qr.png";

    // Open Modal
    closeModal('cart-modal');
    document.getElementById('payment-modal').style.display = 'flex';
}

function verifyAndPrint() {
    // 1. Update the hidden bill with QR code too (for completion)
    const qrImgBill = document.getElementById('invoice-qr');
    const qrImgPayment = document.getElementById('payment-qr-img');
    if (qrImgBill && qrImgPayment) {
        qrImgBill.src = qrImgPayment.src;
    }

    // 2. Generate the actual bill details (populates the invoice HTML)
    generateBill();

    // 3. Close Payment Modal
    closeModal('payment-modal');
}

async function placeOrder() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        alert("Please login to place your order.");
        return;
    }

    if (cart.length === 0) {
        alert("Your cart is empty.");
        return;
    }

    // Prepare items summary and total
    const itemsSummary = cart.map(item => `${item.qty}x ${item.name}`).join(', ');
    let totalPrice = 0;
    cart.forEach(item => totalPrice += item.price * item.qty);

    const payload = {
        userId: user.id,
        itemsSummary: itemsSummary,
        totalPrice: totalPrice,
        status: "NEW"
    };

    try {
        const res = await fetch('http://localhost:9091/api/orders', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert("Order secured! Redirecting to your timeline...");
            cart = []; // Clear local cart
            updateCartCount();
            window.location.href = 'orders.html';
        } else {
            alert("Failed to secure order. Please try again.");
        }
    } catch (err) {
        console.error("Order Error:", err);
        alert("Server connection failed. Is the kitchen open?");
    }
}

