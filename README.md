# Sistema de Doacoes de Alimentos (ODS 2)

Projeto academico em Java solicitado pelo profs Dadiltons, para cadastro e gestao de doacoes de alimentos, alinhado a ODS 2 (Fome Zero).

## Objetivo

Resolver um problema real de organizacao de doacoes com:
- CRUD funcional;
- separacao em camadas;
- aplicacao explicita de padroes de projeto com justificativa tecnica.

## Funcionalidades

- Cadastro de doacao perecivel e nao perecivel
- Listagem de doacoes ordenada por:
	- nome do doador
	- quantidade
- Busca por ID
- Atualizacao de registro
- Remocao com confirmacao
- Cards de resumo no topo do menu:
	- total de doacoes
	- total de pereciveis
	- total de nao pereciveis
- Exportacao da listagem em CSV pelo menu

## Padroes de Projeto Aplicados

- Factory Method
	- [src/br/com/doacao/factory/DoacaoFactory.java](src/br/com/doacao/factory/DoacaoFactory.java)
	- [src/br/com/doacao/factory/DoacaoPereceFactory.java](src/br/com/doacao/factory/DoacaoPereceFactory.java)
	- [src/br/com/doacao/factory/DoacaoNaoPereceFactory.java](src/br/com/doacao/factory/DoacaoNaoPereceFactory.java)
- Singleton
	- [src/br/com/doacao/repository/DoacaoRepository.java](src/br/com/doacao/repository/DoacaoRepository.java)
- Strategy
	- [src/br/com/doacao/strategy/ListagemStrategy.java](src/br/com/doacao/strategy/ListagemStrategy.java)
	- [src/br/com/doacao/strategy/ListarPorNome.java](src/br/com/doacao/strategy/ListarPorNome.java)
	- [src/br/com/doacao/strategy/ListarPorQuantidade.java](src/br/com/doacao/strategy/ListarPorQuantidade.java)
- Adapter
	- [src/br/com/doacao/adapter/DataValidadeAdapter.java](src/br/com/doacao/adapter/DataValidadeAdapter.java)
	- [src/br/com/doacao/adapter/DataValidadeAdapterPadrao.java](src/br/com/doacao/adapter/DataValidadeAdapterPadrao.java)
- Facade
	- [src/br/com/doacao/facade/DoacaoFacade.java](src/br/com/doacao/facade/DoacaoFacade.java)
	- [src/br/com/doacao/facade/DoacaoFacadeImpl.java](src/br/com/doacao/facade/DoacaoFacadeImpl.java)
- Proxy
	- [src/br/com/doacao/proxy/DoacaoFacadeProxy.java](src/br/com/doacao/proxy/DoacaoFacadeProxy.java)

## Arquitetura por Camadas

- View
	- [src/br/com/doacao/view/MenuView.java](src/br/com/doacao/view/MenuView.java)
- Service
	- [src/br/com/doacao/service/DoacaoService.java](src/br/com/doacao/service/DoacaoService.java)
- Repository
	- [src/br/com/doacao/repository/DoacaoRepository.java](src/br/com/doacao/repository/DoacaoRepository.java)
- Model
	- [src/br/com/doacao/model/Doacao.java](src/br/com/doacao/model/Doacao.java)
	- [src/br/com/doacao/model/DoacaoPerecivel.java](src/br/com/doacao/model/DoacaoPerecivel.java)
	- [src/br/com/doacao/model/DoacaoNaoPerecivel.java](src/br/com/doacao/model/DoacaoNaoPerecivel.java)
	- [src/br/com/doacao/model/ResumoDoacoes.java](src/br/com/doacao/model/ResumoDoacoes.java)


## Exportacao CSV

No menu principal:
- escolha a opcao `6`
- informe criterio de ordenacao
- informe nome/caminho do arquivo CSV (ou pressione Enter para `doacoes.csv`)

Formato do arquivo:
- colunas: `id,tipo,doador,item,quantidade,validade`

## Gratidao professor <3, suas aulas foram incriveis!!