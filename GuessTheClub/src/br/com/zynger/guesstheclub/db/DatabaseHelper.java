package br.com.zynger.guesstheclub.db;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.zynger.guesstheclub.R;
import br.com.zynger.guesstheclub.model.Achievement;
import br.com.zynger.guesstheclub.model.Club;
import br.com.zynger.guesstheclub.model.Name;
import br.com.zynger.guesstheclub.model.Phase;
import br.com.zynger.guesstheclub.model.Tip;
import br.com.zynger.guesstheclub.model.User;
import br.com.zynger.guesstheclub.util.AchievementGenerator;
import br.com.zynger.guesstheclub.util.PhaseGenerator;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static String DB_NAME = "guesstheclub.db";
    private static int DB_VERSION = 3;
	
    private Dao<Club, Integer> clubDao = null;
    private Dao<Phase, Integer> phaseDao = null;
    private Dao<Name, Integer> nameDao = null;
    private Dao<Tip, Integer> tipDao = null;
    private Dao<User, Integer> userDao = null;
    private Dao<Achievement, Integer> achievementDao = null;
	private Context ctx;
    
	public DatabaseHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
        this.ctx = ctx;
    }
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource conn) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Club.class);
			TableUtils.createTable(connectionSource, Phase.class);
			TableUtils.createTable(connectionSource, Name.class);
			TableUtils.createTable(connectionSource, Tip.class);
			TableUtils.createTable(connectionSource, User.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
		
		String level = ctx.getResources().getString(R.string.tv_phaseselector_level);
		List<Achievement> achievements = new ArrayList<Achievement>();
		for (Field f :  R.raw.class.getDeclaredFields()) {
			if (f.getName().startsWith("phase")) {
				buildPhase(level, f);
			}else if(f.getName().startsWith("achievements")){
				achievements = AchievementGenerator.getAchievement(ctx);
			}
		}
		
		for (Achievement a : achievements) {
			try {
				Phase p = (Phase) getPhaseDao().queryForEq("constantPhase", a.getConstantPhase()).get(0);
				a.setPhase(p);
				getAchievementDao().create(a);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			getUserDao().create(new User());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void buildPhase(String level, Field f) {
		int i;
		List<Club> clubs;
		i = new Integer(f.getName().split("phase")[1]);
		Phase phase = new Phase();
		phase.setTitle(level + " " + i);
		phase.setConstantPhase(i);
		try {
			getPhaseDao().create(phase);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		clubs = PhaseGenerator.getClubs(ctx, i);
		for (Club club : clubs) {
			club.setPhase(phase);
			try {
				getClubDao().create(club);
				phase.setNumberOfLogos(phase.getNumberOfLogos()+1);
				getPhaseDao().update(phase);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (Name name : club.getNames()) {
				try {
					getNameDao().create(name);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			for (Tip tip : club.getTips()) {
				try {
					getTipDao().create(tip);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// TODO Verificar se tem fase nova e adicionar ao db
	}
	
	public Dao<Club, Integer> getClubDao() throws SQLException {
        if (clubDao == null) {
        	clubDao = getDao(Club.class);
        }
        return clubDao;
    }
	
	public Dao<Phase, Integer> getPhaseDao() throws SQLException {
        if (phaseDao == null) {
        	phaseDao = getDao(Phase.class);
        }
        return phaseDao;
    }
	
	public Dao<Name, Integer> getNameDao() throws SQLException {
        if (nameDao == null) {
        	nameDao = getDao(Name.class);
        }
        return nameDao;
    }
	
	public Dao<Tip, Integer> getTipDao() throws SQLException {
        if (tipDao == null) {
        	tipDao = getDao(Tip.class);
        }
        return tipDao;
    }
	
	public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
        	userDao = getDao(User.class);
        }
        return userDao;
    }

	public Dao<Achievement, Integer> getAchievementDao() throws SQLException {
        if (achievementDao == null) {
        	achievementDao = getDao(Achievement.class);
        }
        return achievementDao;
	}
}
