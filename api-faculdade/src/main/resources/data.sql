INSERT INTO materia(nome, professor)
VALUES
('MT', 'Ruanito'),
('T.I', 'Marcelina'),
('REDES', 'Carolina'),
('Dados', 'Josefa');

INSERT INTO aluno(ra, nome, curso)
VALUES
('02201025', 'Jorginho','1CCOA'),
('02201026', 'Marta', '1ADS'),
('02201027', 'Carlitos', '1ADS'),
('02201028', 'Mariazinha', '3CCOA'),
('02201029', 'Joaozinho', '3CCOA'),
('02201030', 'Fagundes', '3CCOA'),
('02201031', 'Marcos', '1ADSA'),
('02201032', 'Gustavo', '1ADSB'),
('02201033', 'Rafael', '2ADSA');

INSERT INTO aluno_materia(materia_id, aluno_id, nota1, nota2, nota3, nota4)
VALUES
(1, 1, 8.0, 6.0, 10.0, 7.0),
(2, 2, 10.0, 5.0, 1.0, 5.0),
(3, 3, 10.0, 8.0, 10.0, 6.0),
(4, 4, 8.0, 9.0, 10.0, 8.0),
(1, 5, 10.0, 10.0, 10.0, 9.0),
(2, 6, 6.0, 10.0, 10.0, 10.0),
(3, 7, 10.0, 10.0, 10.0, 10.0),
(4, 8, 7.0, 6.0, 10.0, 10.0),
(3, 8, 10.0, 7.0, 7.0, 10.0);