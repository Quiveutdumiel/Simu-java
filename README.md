# Simu-java
Bonjour ,
Ce projet s'inscrit dans l'UV 5.8 (Simulation Java)


How to :
- Changer un scenario : Json file scénarii
- modifier le mouvement des Bateaux : EntityMouvementSequenceur
- L axe X = l'axe des bouées  . L'axe Y est perpendiculaire à X (vers la droite) 
- Le centre du repere est le point de départ des bateaux
- rotation du bateau/drone = SelfRotator(LogicalDateTime d, Point3D positionInitiale, Point3D dirInitiale, Point3D pointAViser,
			double vitRot)
- Mvt Circulaire (pour faire le tour de 360 deg) : CircularMover(LogicalDateTime d, Point3D posInit,Point3D vIni,Point3D cible)
- Mvt Lineaire : RectilinearMover(LogicalDateTime d, Point3D posInit, Point3D target, double vIni)



Questions à poser sur le cahier des charges:

- Taille du bateau
- Taille du drône
- Scenario final pour valider toutes les exigences
