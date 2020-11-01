package br.com.zynger.libertadores.data;

import java.util.Collection;
import java.util.TreeMap;

import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Group;
import br.com.zynger.libertadores.util.GroupEnum;

public class GroupsDB {

	private static GroupsDB ref;
	private TreeMap<GroupEnum, Group> groups;

	private GroupsDB(ClubsDB clubsDB){
		groups = generateGroups(clubsDB.getClubs().values());
	}

	private TreeMap<GroupEnum, Group> generateGroups(Collection<Club> clubs) {
		TreeMap<GroupEnum, Group> tm = new TreeMap<GroupEnum, Group>();
		for (Club club : clubs) {
			GroupEnum clubGroup = club.getGroup();
			if(null == clubGroup){
				Log.e(HomeActivity.TAG, "GROUP NOT DEFINED: " + club.getAcronym());
				continue;
			}
			Group group = tm.get(clubGroup);
			if(null == group){
				group = new Group(clubGroup);
				tm.put(group.getNumber(), group);
			}
			group.addClub(club);
		}
		return tm;
	}

	public static GroupsDB getSingletonObject(ClubsDB clubsDB){
		if (ref == null) ref = new GroupsDB(clubsDB);
		return ref;
	}
	
	public TreeMap<GroupEnum, Group> getGroups() {
		return groups;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
}
