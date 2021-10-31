package ppo.timer.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity
data class TimerEntity(
    var name: String,
    var color: String,
    var warm_up: Int,
    var work: Int,
    var rest: Int,
    var repeats: Int,
    var cycles: Int,
    var cooldown:Int,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) : Parcelable