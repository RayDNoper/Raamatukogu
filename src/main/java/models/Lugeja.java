package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by raydnoper on 19/02/17.
 */
@Table("lugejad")
@Many2Many(other = Teos.class, join = "laenutused", sourceFKName = "lugeja_id", targetFKName = "teos_id")
public class Lugeja extends Model {
}
