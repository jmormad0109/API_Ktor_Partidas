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
('Batalla Épica', 'Ganada', '85%', '01-02-2024', ''),
('Guerra de Clanes', 'Perdida', '60%', '02-02-2024', ''),
('Combate Legendario', 'Empatada', '50%', '03-02-2024', ''),
('Duelo de Maestros', 'Ganada', '92%', '04-02-2024', ''),
('Asedio al Castillo', 'Perdida', '45%', '05-02-2024', ''),
('La Gran Cruzada', 'Ganada', '78%', '06-02-2024', ''),
('Revancha Final', 'Perdida', '33%', '07-02-2024', ''),
('Batalla del Siglo', 'Ganada', '88%', '08-02-2024', ''),
('Tormenta de Espadas', 'Empatada', '50%', '09-02-2024', ''),
('Invasión Oscura', 'Ganada', '81%', '10-02-2024', ''),
('Resistencia Heroica', 'Perdida', '49%', '11-02-2024', ''),
('El Último Asalto', 'Ganada', '95%', '12-02-2024', ''),
('Guerra Santa', 'Perdida', '38%', '13-02-2024', ''),
('El Juicio Final', 'Ganada', '90%', '14-02-2024', ''),
('Conquista Absoluta', 'Ganada', '100%', '15-02-2024', '');
