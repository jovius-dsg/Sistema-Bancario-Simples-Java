package Crud;

import ContaSrc.ContaPoupanca;
import ContaSrc.Conta;
import ContaSrc.ContaCorrente;
import ContaSrc.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class CRUD {

    public static List<Conta> listaContas = new ArrayList<Conta>();
    
    public static boolean criarConta(Cliente cliente, String tipo) {
        ImageIcon icon = new ImageIcon("C:\\Users\\joaov\\Documents\\NetBeansProjects\\GNBVersaoFinal\\src\\JFrame\\mensage-icon.png");

        try {
            Conta conta;
            Cliente clienteExistente;
            String cpfExistente;
            String tipoExistente;

            /*esse laço verifica se já existe uma conta com o mesmo CPF e do mesmo tipo
              se existir, ele avisa e exibe os dados da conta existente
              se não existir, ele cria a conta e exibe os dados da nova conta  
             */
            for (Conta contaExistente : listaContas) {
                clienteExistente = contaExistente.getCliente();
                cpfExistente = clienteExistente.getCPF();
                tipoExistente = contaExistente.tipo();
                if (cpfExistente.equals(cliente.getCPF())
                        && tipoExistente.equals(tipo)) {   
                    JOptionPane.showMessageDialog(null, "Esse Cliente já possui conta " + tipo + " no Banco!", "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
                    JOptionPane.showMessageDialog(null, contaExistente.imprimirInfos(), "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
                    return false;
                }
            }

            //aqui ele verifica que tipo de conta deve ser criada, Corrente ou Poupança
            if ("Corrente".equals(tipo)) {
                conta = new ContaCorrente(cliente);
            } else {
                conta = new ContaPoupanca(cliente);
            }

            listaContas.add(conta);
            JOptionPane.showMessageDialog(null, conta.imprimirInfos(), "GNB", JOptionPane.INFORMATION_MESSAGE, icon);

        } catch (Exception e) {
            return false;

        }

        return true;
    }

    public static boolean deletarContaPorNumero(int numero) {
        ImageIcon icon = new ImageIcon("C:\\Users\\joaov\\Documents\\NetBeansProjects\\GNBVersaoFinal\\src\\JFrame\\mensage-icon.png");

        try {
            /*nesse laço ele procura a conta que deve ser deletada
              e, caso encontre, exibe os dados da conta e pede confirmação ao usuário
              caso não encontre, informa que a conta não foi encontrada
             */
            for (Conta conta : listaContas) {
                int c = listaContas.indexOf(conta);
                if (conta.getNumero() == numero) {
                    int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir essa conta?\n " + conta.imprimirInfos());
                    if (escolha == 0) {
                        listaContas.remove(conta);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Operação Cancelada", "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
                    }
                } else {
                    if (c == (listaContas.size() - 1)) {
                        JOptionPane.showMessageDialog(null, "Conta não encontrada!", "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
                    }
                }

            }

        } catch (Exception e) {
            return false;

        }

        return false;
    }

    public static Conta acessarContaPorNumero(int numero) {
        ImageIcon icon = new ImageIcon("C:\\Users\\joaov\\Documents\\NetBeansProjects\\GNBVersaoFinal\\src\\JFrame\\mensage-icon.png");

        try {
            /*nesse laço ele procura a conta que possui o mesmo número informado
              caso encontre, ele retorna a conta
              caso não encontre, ele informa que a conta não foi encontrada
             */
            for (Conta conta : listaContas) {
                int c = listaContas.indexOf(conta);
                if (conta.getNumero() == numero) {
                    return conta;
                } else {
                    if (c == (listaContas.size() - 1)) {
                        JOptionPane.showMessageDialog(null, "Conta não encontrada!", "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
                    }
                }

            }

        } catch (Exception e) {
            return null;

        }
        return null;
    }

    public static List<Conta> listarContas() {

        return listaContas;

    }

    public static boolean alterarConta(Cliente cliente, int numero, String tipo) {
        ImageIcon icon = new ImageIcon("C:\\Users\\joaov\\Documents\\NetBeansProjects\\GNBVersaoFinal\\src\\JFrame\\mensage-icon.png");

        try {
            /*procura  conta a ser deletada
              se encontrar deleta
              se não encontra, informa que a conta não foi encontrada
             */
            for (Conta conta1 : listaContas) {
                int c = listaContas.indexOf(conta1);
                if (conta1.getNumero() == numero) {
                    listaContas.remove(conta1);
                    if ("Corrente".equals(tipo)) {
                        criarConta(cliente, tipo);
                    } else {
                        criarConta(cliente, tipo);
                    }

                } else {
                    if (c == (listaContas.size() - 1)) {
                        JOptionPane.showMessageDialog(null, "Conta não encontrada!", "GNB", JOptionPane.INFORMATION_MESSAGE, icon);
                    }
                }
            }

        } catch (Exception e) {
            return false;

        }

        return true;

    }

}
