INSERT INTO `empresa` (`id`, `cnpj`, `atualizacao`, `criacao`, `razao_social`)
VALUES (NULL, '82198127000121', CURRENT_DATE(), CURRENT_DATE(), 'Kazale IT');

INSERT INTO `funcionario` (`id`, `cpf`, `atualizacao`, `criacao`, `email`, `nome`,
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`) 
VALUES (NULL, '16248890935', CURRENT_DATE(), CURRENT_DATE(), 'admin@kazale.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL, 
'$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', NULL, 
(SELECT `id` FROM `empresa` WHERE `cnpj` = '82198127000121'));
