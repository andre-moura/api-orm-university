package br.com.bandtec.apifaculdade.scheduler;

import br.com.bandtec.apifaculdade.classes.FilaObj;
import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoAvisos {

    @Autowired
    private AlunoRepository alunoRepository;

    private FilaObj<Aluno> filaPagamentosBoletos = new FilaObj<>(60);
    private FilaObj<Aluno> aux = new FilaObj<>(60);

    /* Todos os dias a cada 10 segundos será enviado a mensagem cobrando o pagamento do boleto,
    a não ser que pague o boleto seja pago e o usuário tirado da filaPagementosBoleto*/
    @Scheduled(fixedRate = 10000, initialDelay = 3000)
    public void cobrarPagamentoBoleto(){
        if (!filaPagamentosBoletos.isEmpty()){
            for (int i = 0; i < filaPagamentosBoletos.size(); i++) {

                Aluno alunoEnviadoMensagem = filaPagamentosBoletos.poll();
                aux.insert(alunoEnviadoMensagem);

                alunoEnviadoMensagem.setCaixaDeEntrada("Você tem um boleto para pagar!");
                System.out.println(alunoEnviadoMensagem.getCaixaDeEntrada());

                // Volta para a fila
                filaPagamentosBoletos.insert(alunoEnviadoMensagem);
            }
        }
    }

    public FilaObj<Aluno> getFilaPagamentosBoletos() {
        return filaPagamentosBoletos;
    }

    public void setFilaPagamentosBoletos(FilaObj<Aluno> filaPagamentosBoletos) {
        this.filaPagamentosBoletos = filaPagamentosBoletos;
    }
}