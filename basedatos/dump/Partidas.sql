CREATE TABLE Partida(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    resultado VARCHAR(20),
    estadistica VARCHAR(5),
    fecha VARCHAR(12)
);

CREATE TABLE Usuario(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60),
    dni VARCHAR(10) UNIQUE NOT NULL,
    password VARCHAR(255),
    email VARCHAR(255),
    token VARCHAR(255)
);
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tabla de partidas';

INSERT INTO Partida (nombre, resultado, estadistica, fecha) VALUES
('Batalla1', 'GANADA', '85%', '01-02-2024'),
('Batalla2', 'PERDIDA', '60%', '02-02-2024'),
('Batalla3', 'EMPATADA', '50%', '03-02-2024'),
('Batalla4', 'GANADA', '92%', '04-02-2024'),
('Batalla5', 'PERDIDA', '45%', '05-02-2024'),
('Batalla6', 'GANADA', '78%', '06-02-2024'),
('Batalla7', 'PERDIDA', '33%', '07-02-2024'),
('Batalla8', 'GANADA', '88%', '08-02-2024'),
('Batalla9', 'EMPATADA', '50%', '09-02-2024'),
('Batalla10', 'GANADA', '81%', '10-02-2024'),
('Batalla11', 'GANADA', '95%', '12-02-2024'),
('Batalla12', 'PERDIDA', '49%', '11-02-2024'),
('Batalla13', 'PERDIDA', '38%', '13-02-2024'),
('Batalla14', 'GANADA', '90%', '14-02-2024'),
('Batalla15', 'GANADA', '100%', '15-02-2024');

INSERT INTO Usuario (name, dni, password, email, token) VALUES
('Antonio', '12345678A', '4ee3679892e6ac5a5b513eba7fd529d363d7a96508421c5dbd44b01b349cf514', 'antonio@gmail.com', ''),
('Maria', '23456789B', '94aec9fbed989ece189a7e172c9cf41669050495152bc4c1dbf2a38d7fd85627', 'maria@gmail.com', ''),
('Carlos', '34567890C', '7b85175b455060e3237e925f023053ca9515e8682a83c8b09911c724a1f8b75f', 'carlos@gmail.com', ''),
('Laura', '45678901D', '5d702eb07928ed7b84626b777c86c39bf4cb403d4024f031d5f97a4b0664421f', 'laura@gmail.com', ''),
('Pedro', '56789012E', 'ee5cd7d5d96c8874117891b2c92a036f96918e66c102bc698ae77542c186f981', 'pedro@gmail.com', ''),
('Sofia', '67890123F', '86846e84f9bfb2053a4823193843d22a0be24a75d304d6f8a2e4d2fac445efaa', 'sofia@gmail.com', ''),
('Javier', '78901234G', '384dac3368de6f658d7bc66e8fd4c8206b91c17a9084498948c7dd6e44d4a055', 'javier@gmail.com', ''),
('Elena', '89012345H', '0ce93c9606f0685bf60e051265891d256381f639d05c0aec67c84eec49d33cc1', 'elena@gmail.com', ''),
('David', '90123456I', '07d046d5fac12b3f82daf5035b9aae86db5adc8275ebfbf05ec83005a4a8ba3e', 'david@gmail.com', ''),
('Lucia', '01234567J', '6326e0e8cfdaab9af83026a0620bafd05179e3a0cd1b812222682d86285b30cc', 'lucia@gmail.com', '');

