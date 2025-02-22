CREATE TABLE Partida(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    resultado VARCHAR(20),
    estadistica VARCHAR(5)
    fecha VARCHAR(12)
    token VARCHAR(255)
);
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tabla de partidas';

INSERT INTO Partida (nombre, resultado, estadistica, fecha, token) VALUES
('Batalla Épica', 'GANADA', '85%', '01-02-2024', ''),
('Guerra de Clanes', 'PERDIDA', '60%', '02-02-2024', ''),
('Combate Legendario', 'EMPATADA', '50%', '03-02-2024', ''),
('Duelo de Maestros', 'GANADA', '92%', '04-02-2024', ''),
('Asedio al Castillo', 'PERDIDA', '45%', '05-02-2024', ''),
('La Gran Cruzada', 'GANADA', '78%', '06-02-2024', ''),
('Revancha Final', 'PERDIDA', '33%', '07-02-2024', ''),
('Batalla del Siglo', 'GANADA', '88%', '08-02-2024', ''),
('Tormenta de Espadas', 'EMPATADA', '50%', '09-02-2024', ''),
('Invasión Oscura', 'GANADA', '81%', '10-02-2024', ''),
('El Último Asalto', 'GANADA', '95%', '12-02-2024', ''),
('Resistencia Heroica', 'PERDIDA', '49%', '11-02-2024', ''),
('Guerra Santa', 'PERDIDA', '38%', '13-02-2024', ''),
('El Juicio Final', 'GANADA', '90%', '14-02-2024', ''),
('Conquista Absoluta', 'GANADA', '100%', '15-02-2024', '');
