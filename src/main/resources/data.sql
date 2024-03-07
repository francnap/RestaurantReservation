INSERT INTO allergene (nome_allergene) 
VALUES 	('Lattosio'), 
		('Arachidi'), 
		('Glutine'),
		('Soia'),
		('Crostacei');
		
		
INSERT INTO prodotto (disponibile, prezzo, categoria, nome_prodotto) 
VALUES 	(true, 12.5, 'Pizze', 'Pizza Diavola'), 
		(true, 6.0, 'Pizze', 'Pizza Margherita'), 
		(true, 8.5, 'Pizze', 'Pizza Cotto e Mais');
		
		
INSERT INTO prodotti_allergeni (id_allergene, id_prodotto) 
VALUES 	(3, 1), 
		(1, 2), 
		(3, 2), 
		(1, 3), 
		(3, 3);
		
		
INSERT INTO tavolo (numero_posti, utilizzabile, note) 
VALUES 	(1, true, 'Vicino la cucina'), 
		(2, true, 'Fondo sala'), 
		(3, true, 'Centro sala'), 
		(4, true, 'Vicino ai bagni'), 
		(5, true, 'Vicino le scale'), 
		(6, true, 'Fuori dal locale'), 
		(7, true, 'Centro sala'), 
		(8, true, 'Vicino la cassa'), 
		(9, true, 'Ingresso'), 
		(10, true, 'Vicino la finestra');
		
		
INSERT INTO evento (min_durata_evento, descrizione) 
VALUES 	(60, 'Aperitivo'), 
		(75, 'Pranzo'), 
		(100, 'Cena');