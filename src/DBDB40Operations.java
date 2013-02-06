import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;


public class DBDB40Operations extends DB4ODatabase implements DBOperations {

	@Override
	public Billet getBillet(String code) {
		Billet proto = new Billet();
		proto.setCode(code);
		ObjectSet<Billet> result = db.queryByExample(proto);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public Gare getGare(String code) {
		Gare proto = new Gare();
		proto.setCode(code);
		proto.setLatLon(0.0, 0.0);
		ObjectSet<Gare> result = db.queryByExample(proto);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public Trajet getTrajet(String code) {
		Trajet proto = new Trajet();
		proto.setCode(code);
		ObjectSet<Trajet> result = db.queryByExample(proto);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public Passager getPassager(String numSecu) {
		Passager proto = new Passager();
		proto.setNumSecu(numSecu);
		ObjectSet<Passager> result = db.queryByExample(proto);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public Passager getPassagerByNom(String nom) {
		Passager proto = new Passager();
		proto.setNom(nom);
		ObjectSet<Passager> result = db.queryByExample(proto);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<Billet> getBillets() {
		Billet proto = new Billet();
		return db.queryByExample(proto);
	}

	@Override
	public List<Gare> getGares() {
		return db.query(Gare.class);
	}

	@Override
	public List<Trajet> getTrajets() {
		return db.query(Trajet.class);
	}

	@Override
	public List<Passager> getPassagers() {
		return db.query(Passager.class);
	}

	@Override
	public int getNbBillets() {
		// Native query or soda query don't purposes count feature
		return getBillets().size();
	}

	@Override
	public List<Trajet> getTrajetsFromGare(Gare gare) {
		Trajet proto = new Trajet();
		proto.setDepart(gare);
		return db.queryByExample(proto);
	}

	@Override
	public List<Trajet> getTrajetsToGare(Gare gare) {
		Trajet proto = new Trajet();
		proto.setArrivee(gare);
		return db.queryByExample(proto);
	}

	@Override
	public List<Gare> getDestinations(final Gare gare, final Double distance) {
		List<Trajet> trajets = db.query(new Predicate<Trajet>() {
			private static final long serialVersionUID = -7447964717069554260L;

			public boolean match(Trajet trajet) {
				return (trajet.getDepart().equals(gare) &&
					gare.distanceTo(trajet.getArrivee()) < distance);
			}
		});
		
		List<Gare> gares = new ArrayList<>();
		for (Trajet trajet : trajets) {
			gares.add(trajet.getArrivee());
		}
		
		return gares;
	}

	@Override
	public List<Trajet> getTrajets(Passager passager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Trajet> getTrajets(Passager passager1, Passager passager2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Passager> searchPassager(String nom) {
		final String lnom = nom.toLowerCase();
		
		return db.query(new Predicate<Passager>() {
			private static final long serialVersionUID = -7447964717069554262L;

			public boolean match(Passager passager) {
				String nom = passager.getNom();
				return nom != null && nom.toLowerCase().indexOf(lnom) != -1;
			}
		});
	}

	@Override
	public List<Gare> searchGare(String nom) {
		final String lnom = nom.toLowerCase();
		
		return db.query(new Predicate<Gare>() {
			private static final long serialVersionUID = -7447964717069554261L;

			public boolean match(Gare gare) {
				String nom = gare.getNom();
				return nom != null && nom.toLowerCase().indexOf(lnom) != -1;
			}
		});
	}

	@Override
	public List<Double> getPrixBillets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Passager> getPassagersOfTrajet(Trajet trajet) {
		Billet proto = new Billet();
		proto.setTrajet(trajet);
		Set<Passager> passagers = new HashSet<>();
		List<Billet> billets = db.queryByExample(proto);
		
		for (Billet billet : billets) {
			passagers.add(billet.getPassager());
		}
		
		List<Passager> lpassagers = new ArrayList<>();
		lpassagers.addAll(passagers);
		return lpassagers;
	}

}
