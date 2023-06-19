-- Creación de la tabla usuario BACK
CREATE TABLE usuario (
  id_user INT PRIMARY KEY,
  nombre_usuario VARCHAR(50) NOT NULL,
  contrasena VARCHAR(50) NOT NULL,
  nombre_completo VARCHAR(100) NOT NULL,
  fecha_nacimiento DATE,
  email VARCHAR(100),
  telefono VARCHAR(20),
  fec_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  foto_perfil VARCHAR(100),
  privada BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id_user)
);

-- Índices de la tabla usuario
CREATE INDEX idx_usuario_id ON usuario (id_user);
CREATE INDEX idx_usuario_nombre_usuario ON usuario (nombre_usuario);

-- Creación de la tabla publicacion BACK
CREATE TABLE publicacion (
  id_post INT PRIMARY KEY,
  id_user INT NOT NULL,
  texto VARCHAR(280) NOT NULL,
  fec_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id_post, id_user),
  FOREIGN KEY (id_user) REFERENCES usuario(id_user) ON DELETE CASCADE
);

-- Índices de la tabla publicacion
CREATE INDEX idx_publicacion_id ON publicacion (id_post);
CREATE INDEX idx_publicacion_usuario_id ON publicacion (id_user);
CREATE INDEX idx_publicacion_fecha_creacion ON publicacion (fec_creacion);

-- Creación de la tabla comentario BACK
CREATE TABLE comentario (
  id_comment INT PRIMARY KEY,
  id_user INT NOT NULL,
  id_post INT NOT NULL,
  texto VARCHAR(280) NOT NULL,
  fec_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id_comment, id_user, id_post),
  FOREIGN KEY (id_user) REFERENCES usuario(id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_post) REFERENCES publicacion(id_post) ON DELETE CASCADE
);

-- Índices de la tabla comentario
CREATE INDEX idx_comentario_id ON comentario (id_comment);
CREATE INDEX idx_comentario_usuario_id ON comentario (id_user);
CREATE INDEX idx_comentario_publicacion_id ON comentario (id_post);
CREATE INDEX idx_comentario_fecha_creacion ON comentario (fec_creacion);

-- Creación de la tabla historia
CREATE TABLE historia (
  id_story INT PRIMARY KEY,
  id_user INT NOT NULL,
  texto VARCHAR(280) NOT NULL,
  fec_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id_story, id_user),
  FOREIGN KEY (id_user) REFERENCES usuario(id_user) ON DELETE CASCADE
);

-- Índices de la tabla historia
CREATE INDEX idx_historia_id ON historia (id_story);
CREATE INDEX idx_historia_usuario_id ON historia (id_user);
CREATE INDEX idx_historia_fecha_creacion ON historia (fec_creacion);

-- Creación de la tabla mensaje_privado BACK
CREATE TABLE mensaje_privado (
  id_mensaje INT PRIMARY KEY,
  id_usuario_emisor INT NOT NULL,
  id_usuario_receptor INT NOT NULL,
  texto VARCHAR(280) NOT NULL,
  fec_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id_mensaje, id_usuario_emisor, id_usuario_receptor),
  FOREIGN KEY (id_usuario_emisor) REFERENCES usuario(id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_usuario_receptor) REFERENCES usuario(id_user) ON DELETE CASCADE
);

-- Índices de la tabla mensaje_privado
CREATE INDEX idx_mensaje_privado_id ON mensaje_privado (id_mensaje);
CREATE INDEX idx_mensaje_privado_usuario_emisor_id ON mensaje_privado (id_usuario_emisor);
CREATE INDEX idx_mensaje_privado_usuario_receptor_id ON mensaje_privado (id_usuario_receptor);
CREATE INDEX idx_mensaje_privado_fecha_envio ON mensaje_privado (fec_envio);

-- Creación de la tabla configuracion
CREATE TABLE configuracion (
  id_config INT PRIMARY KEY,
  id_user INT NOT NULL,
  notificaciones BOOLEAN NOT NULL,
  mostrar_edad BOOLEAN NOT NULL,
  PRIMARY KEY(id_config, id_user),
  FOREIGN KEY (id_user) REFERENCES usuario(id_user) ON DELETE CASCADE
);

-- Creación de la tabla seguidores
CREATE TABLE seguidores (
  id_seguidor INT NOT NULL,
  id_seguido INT NOT NULL,
  fec_seguimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_seguidor, id_seguido),
  FOREIGN KEY (id_seguidor) REFERENCES usuario(id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_seguido) REFERENCES usuario(id_user) ON DELETE CASCADE
);

-- Índices de la tabla seguidores
CREATE INDEX idx_seguidores_seguidor_id ON seguidores (id_seguidor);
CREATE INDEX idx_seguidores_seguido_id ON seguidores (id_seguido);
CREATE INDEX idx_seguidores_fecha_seguimiento ON seguidores (fec_seguimiento);

  -- Creación de la tabla solicitudes BACK
CREATE TABLE solicitudes (
  id_request INT NOT NULL AUTO_INCREMENT,
  id_solicitante INT NOT NULL,
  id_receptor INT NOT NULL,
  fec_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  estado ENUM('pendiente', 'aceptada', 'rechazada') DEFAULT 'pendiente',
  PRIMARY KEY (id_request),
  FOREIGN KEY (id_solicitante) REFERENCES usuario(id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_receptor) REFERENCES usuario(id_user) ON DELETE CASCADE
);

-- Índices de la tabla solicitudes
CREATE INDEX idx_solicitudes_solicitante_id ON solicitudes (id_solicitante);
CREATE INDEX idx_solicitudes_receptor_id ON solicitudes (id_receptor);
CREATE INDEX idx_solicitudes_fecha_solicitud ON solicitudes (fec_solicitud);

-- Creación de la tabla hobby BACK
CREATE TABLE hobby (
  id_hobby INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(200),
  PRIMARY KEY (id_hobby)
);

-- Índices de la tabla hobby
CREATE INDEX idx_hobby_nombre ON hobby (nombre);

-- Creación de la tabla usuario_hobby
CREATE TABLE usuario_hobby (
  id_user INT NOT NULL,
  id_hobby INT NOT NULL,
  PRIMARY KEY (id_user, id_hobby),
  FOREIGN KEY (id_user) REFERENCES usuario(id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_hobby) REFERENCES hobby(id_hobby) ON DELETE CASCADE
);

-- Índices de la tabla usuario_hobby
CREATE INDEX idx_usuario_hobby_usuario_id ON usuario_hobby (id_user);
CREATE INDEX idx_usuario_hobby_hobby_id ON usuario_hobby (id_hobby);