// Base URL for API
const API_BASE = '/api';

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

async function sendLoginOtp() {
    const mobile = document.getElementById('user-mobile').value;
    if (!mobile) return alert("Please enter mobile number");

    try {
        const response = await fetch(`${API_BASE}/auth/send-otp`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ mobile })
        });
        const data = await response.json();
        alert(`OTP Sent! (For Demo: ${data.otp})`);

        document.getElementById('otp-section').style.display = 'block';
        document.getElementById('send-otp-btn').style.display = 'none';
        document.getElementById('login-btn').style.display = 'block';
    } catch (e) {
        console.error(e);
        alert("Server connection failed. Is the backend running?");
    }
}

async function handleUserLogin(e) {
    e.preventDefault();
    const mobile = document.getElementById('user-mobile').value;
    const otp = document.getElementById('user-otp').value;

    try {
        const res = await fetch(`${API_BASE}/auth/customer-login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ mobile, otp })
        });

        if (res.ok) {
            const user = await res.json();
            localStorage.setItem('user', JSON.stringify(user));
            localStorage.removeItem('staff'); // Clear staff session
            alert("Login Successful! Redirecting...");
            window.location.href = 'index.html';
        } else {
            alert("Invalid OTP or Login Failed");
        }
    } catch (e) {
        alert("Error connecting to server");
    }
}

// Tab Switching for Index Modal
window.switchTab = function (type) {
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    document.getElementById('user-login').style.display = 'none';
    document.getElementById('staff-login').style.display = 'none';
    document.getElementById('admin-login').style.display = 'none';

    if (type === 'user') {
        document.getElementById('user-login').style.display = 'flex'; // or block
        document.querySelector('.tabs button:nth-child(1)').classList.add('active');
    } else if (type === 'staff') {
        document.getElementById('staff-login').style.display = 'flex';
        document.querySelector('.tabs button:nth-child(2)').classList.add('active');
    } else if (type === 'admin') {
        document.getElementById('admin-login').style.display = 'flex';
        document.querySelector('.tabs button:nth-child(3)').classList.add('active');
    }
};

async function handleStaffLogin(e) {
    e.preventDefault();
    const isStaffForm = e.target.id === 'staff-form' || e.target.id === 'staff-login';
    const id = isStaffForm ? document.getElementById(e.target.id === 'staff-form' ? 'staff-id' : 'emp-id').value : document.getElementById('admin-id').value;
    const pass = isStaffForm ? document.getElementById(e.target.id === 'staff-form' ? 'staff-pass' : 'emp-pass').value : document.getElementById('admin-pass').value;

    try {
        const res = await fetch(`${API_BASE}/auth/staff-login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id, password: pass })
        });

        if (res.ok) {
            const emp = await res.json();
            localStorage.setItem('staff', JSON.stringify(emp));
            localStorage.removeItem('user'); // Clear user session
            alert(`Welcome, ${emp.name}!`);

            // Redirect based on role
            if (emp.role === 'Chef') window.location.href = 'chef.html';
            else if (emp.role === 'Head' || emp.department === 'Management') window.location.href = 'admin.html';
            else window.location.href = 'staff.html';

        } else {
            alert("Invalid Credentials");
        }
    } catch (e) {
        alert("Login Error: Is the server running?");
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
                    <span>$${item.price}</span>
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
        list.innerHTML = '<p style="text-align:center; color:#888;">Your cart is empty.</p>';
    } else {
        cart.forEach((item, index) => {
            const itemTotal = item.price * item.qty;
            total += itemTotal;

            const div = document.createElement('div');
            div.className = 'cart-item';
            div.innerHTML = `
                <div>
                    <div style="font-weight:bold; color:var(--accent-color);">${item.name}</div>
                    <div style="font-size:0.9rem;">$${item.price} x ${item.qty}</div>
                </div>
                <div style="display:flex; align-items:center; gap:10px;">
                    <span style="font-weight:bold;">$${itemTotal.toFixed(2)}</span>
                    <button onclick="removeFromCart(${index})" style="background:red; color:white; border:none; border-radius:5px; padding:2px 5px; cursor:pointer;">×</button>
                </div>
            `;
            list.appendChild(div);
        });
    }
    document.getElementById('cart-total').innerText = total.toFixed(2);
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
    const date = new Date().toLocaleString();
    const orderId = Math.floor(Math.random() * 1000000);

    document.getElementById('bill-date').innerText = date;
    document.getElementById('bill-id').innerText = orderId;

    const tbody = document.getElementById('bill-items');
    tbody.innerHTML = '';
    let grandTotal = 0;

    cart.forEach(item => {
        const total = item.price * item.qty;
        grandTotal += total;

        tbody.innerHTML += `
            <tr style="border-bottom:1px solid #ccc;">
                <td style="padding:10px 5px;">${item.name}</td>
                <td style="padding:10px 5px;">${item.qty}</td>
                <td style="padding:10px 5px; text-align:right;">$${item.price}</td>
                <td style="padding:10px 5px; text-align:right;">$${total.toFixed(2)}</td>
            </tr>
        `;
    });

    document.getElementById('bill-total').innerText = grandTotal.toFixed(2);

    // Close modal and print
    closeModal('cart-modal');
    window.print();
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

