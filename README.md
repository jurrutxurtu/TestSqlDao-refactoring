# TestSqlDao-refactoring
Refactorizacion de una clase DAO propuesta.

###Varias consideraciones:

 Sin disponer de mas información del sistema, diría que un Singlenton no tiene mucho sentido para esta clase.
 Veo mas correcto tener una fabrica de Daos (Factory Pattern) de la que se sirvan los clientes de este Dao para obtener una      instancia.
 Incluso desde esa fábrica se podría forzar que la instancia de TestSqlDao fuese única en el sistema si asi se quisiese.
 
 Declarar la variable maxOrderUser como variable de clase combinado con el patrón Singleton para esta clase puede ser MUY peligroso.
 Ante una modificación despistadada del método getMaxUserOrderId podríamos encontrarnos con que este mapa está contaminado con resultados de consultas previas, por ejemplo.
 
Quedan pendientes las consideraciones de la refactorización de cada método.

