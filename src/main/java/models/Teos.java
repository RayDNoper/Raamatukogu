package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;


/**
 * Created by raydnoper on 18/02/17.
 */
@Table("teosed")
@Many2Many(other = Autor.class, join = "autor_teos", sourceFKName = "teos_id", targetFKName = "autor_id")
public class Teos extends Model{

}
