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

    private FilaObj<Aluno> filaEmicaoBoleto = new FilaObj<>(60);
    private FilaObj<Aluno> aux = new FilaObj<>(60);

    // Todos os dias do mês as 21:30:00 será enviado a mensagem, a não ser que pague o boleto!
    @Scheduled(fixedRate = 10000, initialDelay = 3000)
    public void cobrarPagamentoBoleto(){
        if (!filaEmicaoBoleto.isEmpty()){
            for (int i = 0; i < filaEmicaoBoleto.size(); i++) {

                Aluno alunoEnviadoMensagem = filaEmicaoBoleto.poll();
                aux.insert(alunoEnviadoMensagem);

                alunoEnviadoMensagem.setCaixaDeEntrada("Você tem um boleto para pagar!");

                // Volta para a fila
                filaEmicaoBoleto.insert(alunoEnviadoMensagem);
            }
        }
    }

    public FilaObj<Aluno> getFilaEmicaoBoleto() {
        return filaEmicaoBoleto;
    }

    public void setFilaEmicaoBoleto(FilaObj<Aluno> filaEmicaoBoleto) {
        this.filaEmicaoBoleto = filaEmicaoBoleto;
    }
}