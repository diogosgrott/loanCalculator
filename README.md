# Calculadora de Empréstimo



O objetivo desta calculadora é que, a partir dos dados iniciais, ela retorne as prestações detalhadas de pagamento. 



Os seguintes requisitos devem ser satisfeitos:

- Data inicial (Obrigatório): A data de início do cálculo;
- Data final (Obrigatório): A data final do cálculo;
- Primeiro Pagamento (Obrigatório): A data do primeiro pagamento de parcela;
- Valor do empréstimo (Obrigatório): O valor do empréstimo;
- Taxa de juros (Obrigatório): A taxa de juros a ser aplicada no empréstimo;
- A data final deve ser maior que a data inicial;
- A data de primeiro pagamento deve ser maior que a data inicial e menor que a data final;
- Valor de empréstimo deve ser maior que zero;
- Taxa de juros deve ser maior que zero;



Além disso, outros requisitos são considerados:

- A data final sempre será um pagamento de parcela;
- A data inicial, data final e todas as datas de fim de mês entre a data inicial e a data final
  são mostradas na grid com seus respectivos valores;
- Todos os meses entre a data de primeiro pagamento e a data final possuem uma parcela no dia do primeiro pagamento;



O projeto foi feito através do JavaSpringBoot e por isso é possível subir localmente. Para testar a API diretamente, segue a documentação da API
curl -X GET "http://localhost:8080/loan/postLoanData?startDate=&endDate=&firstPaymentDate=&loanAmount=&taxRate="

- Long startDate -> valor em milisegundos da data inicial do empréstimo;
- Long endDate -> valor em milisegundos da data final do empréstimo;
- Long firstPaymentDate -> valor em milisegundos da data do primeiro pagamento;
- Double loanAmount -> valor do empréstimo;
- Double taxRate -> valor da taxa de juros em valor decimal (7% deve ser mandado como 0.07)