package typestore.typereport.models.submodels;

public class ReportVariaveis {
	
	private String player_que_reportou, player_que_foi_reportado, motivo, prova, data;
	
	public ReportVariaveis(String player_que_reportou, String player_que_foi_reportado, String motivo, String prova,
			String data) {
		this.player_que_reportou = player_que_reportou;
		this.player_que_foi_reportado = player_que_foi_reportado;
		this.motivo = motivo;
		this.prova = prova;
		this.data = data;
	}

	public String getPlayer_que_reportou() {
		return player_que_reportou;
	}

	public void setPlayer_que_reportou(String player_que_reportou) {
		this.player_que_reportou = player_que_reportou;
	}

	public String getPlayer_que_foi_reportado() {
		return player_que_foi_reportado;
	}

	public void setPlayer_que_foi_reportado(String player_que_foi_reportado) {
		this.player_que_foi_reportado = player_que_foi_reportado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getProva() {
		return prova;
	}

	public void setProva(String prova) {
		this.prova = prova;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
