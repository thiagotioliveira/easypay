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

CREATE TABLE wallet (
  id UUID DEFAULT RANDOM_UUID(),
  user_id UUID NOT NULL,
  amount numeric NOT NULL DEFAULT 0,
  CONSTRAINT wallet_pkey PRIMARY KEY (id),
  CONSTRAINT wallet_unique_user_id UNIQUE (user_id),
  CONSTRAINT wallet_0_fk FOREIGN KEY (user_id) REFERENCES user1(id)
);