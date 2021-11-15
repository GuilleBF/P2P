CREATE TABLE usuario
(
    nombre character varying COLLATE pg_catalog."default" NOT NULL,
    contrasenha character varying COLLATE pg_catalog."default",
    CONSTRAINT "Usuario_pkey" PRIMARY KEY (nombre)
);

CREATE TABLE amigo
(
    usuario1 character varying COLLATE pg_catalog."default" NOT NULL,
    usuario2 character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Amigo_pkey" PRIMARY KEY (usuario1, usuario2),
    CONSTRAINT "Usuario1" FOREIGN KEY (usuario1)
        REFERENCES public.usuario (nombre) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "Usuario2" FOREIGN KEY (usuario2)
        REFERENCES public.usuario (nombre) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE solicitud
(
    solicitante character varying COLLATE pg_catalog."default" NOT NULL,
    solicitado character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Solicitud_pkey" PRIMARY KEY (solicitante, solicitado),
    CONSTRAINT "Solicitado" FOREIGN KEY (solicitado)
        REFERENCES public.usuario (nombre) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "Solicitante" FOREIGN KEY (solicitante)
        REFERENCES public.usuario (nombre) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
