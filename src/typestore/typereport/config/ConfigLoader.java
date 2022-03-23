package typestore.typereport.config;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import typestore.typereport.Main;

public class ConfigLoader {

	@SuppressWarnings("unused")
	private Main m;
	private FileConfiguration config;

	// mysql and sqllite
	public boolean ativar;
	public String host;
	public String user;
	public String password;
	public String database;
	public int port;

	// utilidades
	public boolean console_log;
	public boolean exibir_som;
	public boolean glow_ao_selecionar;
	public int delay_para_reportar;

	// mensagens
	public List<String> comando_incompleto;
	public List<String> Voce_ja_reportou_este_jogador;
	public List<String> report_enviado;
	public List<String> esta_em_delay;
	public List<String> ja_pode_reportar_novamente;
	public List<String> sem_permissao;
	public List<String> report_recebido_staff;
	public List<String> acao_executada;
	public List<String> report_deltado;
	public List<String> digite_motivo;
	public List<String> digite_prova;
	public List<String> operacao_cancelada;

	public ConfigLoader(Main m) {
		this.m = m;
		config = m.getConfig();
		try {
			loadMysqlConfig();
			loadUtilidadesConfig();
			loadMensagensConfig();
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(m.getPrefix() + " §cNao foi possivel carregar a config.yml!");
		}
	}

	private void loadMysqlConfig() {
		String a = "MySQL.";
		ativar = config.getBoolean(a + "ativar");
		host = config.getString(a + "host");
		user = config.getString(a + "user");
		password = config.getString(a + "password");
		database = config.getString(a + "database");
		port = config.getInt(a + "port");
	}

	private void loadUtilidadesConfig() {
		String a = "Utildades.";
		console_log = config.getBoolean(a + "console_log");
		exibir_som = config.getBoolean(a + "exibir_som");
		glow_ao_selecionar = config.getBoolean(a + "glow_ao_selecionar");
		delay_para_reportar = config.getInt(a + "delay_para_reportar");

	}

	private void loadMensagensConfig() {
		String a = "Mensagens.";
		List<String> comando_incompleto1 = config.getStringList(a + "comando_incompleto");
		comando_incompleto = (List<String>) comando_incompleto1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());

		List<String> Voce_ja_reportou_este_jogador1 = config.getStringList(a + "Voce_ja_reportou_este_jogador");
		Voce_ja_reportou_este_jogador = (List<String>) Voce_ja_reportou_este_jogador1.stream()
				.map(s -> s.replace("&", "§")).collect(Collectors.toList());

		List<String> report_enviado1 = config.getStringList(a + "report_enviado");
		report_enviado = (List<String>) report_enviado1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());

		List<String> esta_em_delay1 = config.getStringList(a + "esta_em_delay");
		esta_em_delay = (List<String>) esta_em_delay1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());
		List<String> ja_pode_reportar_novamente1 = config.getStringList(a + "ja_pode_reportar_novamente");
		ja_pode_reportar_novamente = (List<String>) ja_pode_reportar_novamente1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());

		List<String> sem_permissao1 = config.getStringList(a + "sem_permissao");
		sem_permissao = (List<String>) sem_permissao1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());

		List<String> report_recebido_staff1 = config.getStringList(a + "report_recebido_staff");
		report_recebido_staff = (List<String>) report_recebido_staff1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());

		List<String> acao_executada1 = config.getStringList(a + "acao_executada");
		acao_executada = (List<String>) acao_executada1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());

		List<String> report_deltado1 = config.getStringList(a + "report_deltado");
		report_deltado = (List<String>) report_deltado1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());
		
		List<String> digite_motivo1 = config.getStringList(a + "digite_motivo");
		digite_motivo = (List<String>) digite_motivo1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());
		
		List<String> digite_prova1 = config.getStringList(a + "digite_prova");
		digite_prova = (List<String>) digite_prova1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());
		
		List<String> operacao_cancelada1 = config.getStringList(a + "operacao_cancelada");
		operacao_cancelada = (List<String>) operacao_cancelada1.stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());
	}
}
