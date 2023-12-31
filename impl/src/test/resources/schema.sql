CREATE TABLE user1 (
  id UUID DEFAULT RANDOM_UUID(),
  name VARCHAR(100) NOT NULL,
  cpf_cnpj VARCHAR(14) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(50) NOT NULL,
  role VARCHAR(50) NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY (id),
  CONSTRAINT user_unique_cpf_cnpj UNIQUE (cpf_cnpj),
  CONSTRAINT user_unique_email UNIQUE (email)
);