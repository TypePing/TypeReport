package typestore.typereport.models;

import java.util.List;

import typestore.typereport.models.submodels.ReportVariaveis;

public class Report {
	
	private String player;
	private List<ReportVariaveis> reports;
	
	public Report(String player, List<ReportVariaveis> reports) {
		this.player = player;
		this.reports = reports;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public List<ReportVariaveis> getReports() {
		return reports;
	}
	public void setReports(List<ReportVariaveis> reports) {
		this.reports = reports;
	}

}
