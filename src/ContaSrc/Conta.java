package ContaSrc;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public abstract class Conta implements IConta {

    private static final int AGENCIA_PADRAO = 1; //toda conta criada tem a mesma agencia
    private final double LIMITE = 5000; //a conta não aceita operações acima de R$5000.00
    private static int SEQUENCIAL = 1;  //o número da conta é representado pelo valor do sequencial, a 1ª conta tem número 1, a 2ª tem número 2 e assim por diante
    private final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    private int agencia;
    private int numero;
    private double saldo;
    private Cliente cliente;
    private String historico = "";
    private Date data;

    public Conta(Cliente cliente) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
    }

    @Override
    public void sacar(double valor) {
        ImageIcon icon = new ImageIcon("C:\\Users\\joaov\\Documents\\NetBeansProjects\\GNBVersaoFinal\\src\\JFrame\\mensage-icon.png");
        try {
            //Ocorre erro se o saldo for insuficiente ou o valor de saque for acima do limite
            if (saldo < valor) {
                throw new ContaException("Saldo insuficiente!\n");
            }
            if (valor > LIMITE) {
                throw new ContaException("Limite ultrapassado!\n");
            }
            saldo -= valor;
            data = new Date();
            historico += "\n" + formatoData.format(data) + ": Saque de R$" + valor;
        } catch (ContaException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        data = new Date();
        historico += "\n" + formatoData.format(data) + ": Deposito de R$" + valor;
    }

    @Override
    public void transferir(double valor, Conta contaDestino) {
        ImageIcon icon = new ImageIcon("C:\\Users\\joaov\\Documents\\NetBeansProjects\\GNBVersaoFinal\\src\\JFrame\\mensage-icon.png");
        try {
            //Ocorre erro se o saldo for insuficiente, o valor de transferência for acima do limite
            //ou a conta destino da transfência tiver o mesmo número
            if (saldo < valor) {
                throw new ContaException("Saldo insuficiente!\n");
            }
            if (valor > LIMITE) {
                throw new ContaException("Limite ultrapassado!\n");
            }
            if (contaDestino.getNumero() == this.numero) {
                throw new ContaException("O número informado é o desta conta");
            }
            this.sacar(valor);
            contaDestino.depositar(valor);
            data = new Date();
            historico += "\n" + formatoData.format(data) + ": Transferencia de R$" + valor
                    + " para a conta " + contaDestino.numero;
        } catch (ContaException e) {

            JOptionPane.showMessageDialog(null, e.toString(), "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    @Override
    public String tipo() {
        return "";
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    //imprime as informações básicas da conta
    public String imprimirInfos() {
        return "Titular: " + this.cliente.getNome()
                + "\nCPF: " + this.cliente.getCPF()
                + "\nAgência: " + this.agencia
                + "\nNúmero: " + this.numero
                + "\nTipo: " + tipo()
                + String.format("\nSaldo: %.2f", this.saldo);
    }

    //imprime o histórico de transações da conta
    public String imprimirHistorico() {
        return "\n==================="
                + historico
                + "\n===================";

    }

    @Override
    public abstract void imprimirExtrato();
}
