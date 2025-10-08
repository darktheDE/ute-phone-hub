import bcrypt

# Thông tin admin
email = "test@gmail.com"
full_name = "UTE Admin"
password = "123456"

# Hash password bằng BCrypt
hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
hashed_str = hashed.decode('utf-8')

# Xuất câu lệnh SQL (giả sử bảng user có các cột: id, full_name, email, password, role, status)
print("INSERT INTO \"user\" (full_name, email, password, role, status) VALUES")
print(f"('{full_name}', '{email}', '{hashed_str}', 'ADMIN', 'ACTIVE');")