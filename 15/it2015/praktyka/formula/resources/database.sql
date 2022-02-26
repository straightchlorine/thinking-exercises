CREATE TABLE `kierowcy` (
  `Id_kierowcy` text,
  `Nazwisko` text,
  `Imie` text,
  `Kraj` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `wyscigi` (
  `Id_wyscigu` text,
  `Rok` int DEFAULT NULL,
  `GrandPrix` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `wyniki` (
  `Id_kierowcy` text,
  `Punkty` int DEFAULT NULL,
  `Id_wyscigu` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
