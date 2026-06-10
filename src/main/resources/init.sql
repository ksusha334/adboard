DROP TABLE IF EXISTS ads CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE ads (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    price DOUBLE PRECISION NOT NULL,
    image_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_ads_created_at ON ads(created_at DESC);
CREATE INDEX idx_users_username ON users(username);

INSERT INTO users (username, password, email, role, created_at)
VALUES (
    'testuser',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EHs',
    'test@example.com',
    'USER',
    NOW()
) ON CONFLICT (username) DO NOTHING;

DO $$
DECLARE
    i INTEGER;
    random_price DECIMAL;
    image_url TEXT;
BEGIN
    FOR i IN 1..100 LOOP
        random_price := 1000 + (i * 100) % 50000;
        image_url := 'https://picsum.photos/300/200?random=' || i;
        
        INSERT INTO ads (title, description, price, image_url, created_at)
        VALUES (
            'Объявление ' || i,
            'Полное описание объявления номер ' || i || '. Здесь может быть любой текст.',
            random_price,
            image_url,
            NOW() - (i || ' days')::INTERVAL
        );
    END LOOP;
END $$;

SELECT 'Количество объявлений: ' || COUNT(*) AS result FROM ads;
SELECT 'Количество пользователей: ' || COUNT(*) AS result FROM users;

SELECT id, title, price, created_at 
FROM ads 
ORDER BY created_at DESC 
LIMIT 5;
