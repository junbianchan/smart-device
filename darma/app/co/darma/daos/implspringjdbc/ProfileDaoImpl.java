package co.darma.daos.implspringjdbc;

import co.darma.daos.ProfileDao;
import co.darma.daos.implspringjdbc.init.JDBCTemplateFactory;
import co.darma.daos.implspringjdbc.rowmappers.MemberProfileRowMapper;
import co.darma.models.data.MemberProfile;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class ProfileDaoImpl implements ProfileDao {

    private JdbcTemplate t = JDBCTemplateFactory.getJdbcTemplate();

    public static ProfileDao createInstance() {
        return new ProfileDaoImpl();
    }

    @Override
    public int updateProfile(MemberProfile profile) {
        final String sql = "UPDATE member_profiles "
                + "SET first_name = ?, last_name = ?, gender = ?, weight = ?, height = ?, birthday = ?, img_large = ?, img_medium = ?, img_small = ? "
                + "WHERE member_id = ?";
        final Object[] params = new Object[]{profile.firstName, profile.lastName, profile.gender, profile.weight,
                profile.height, profile.birthday, profile.imgLarge, profile.imgMedium, profile.imgSmall, profile.memberId};
        return t.update(sql, params);
    }

    @Override
    public MemberProfile getProfileByMemberId(Long memberId)
            throws SQLException {
        final String sql = "SELECT * FROM member_profiles WHERE member_id = ?";
        Object[] params = new Object[]{memberId};
        List<MemberProfile> profiles = t.query(sql, params, new MemberProfileRowMapper());
        if (profiles.size() == 1) {
            return profiles.get(0);
        }
        return null;
    }


    @Override
    public int createOrUpdateProfile(MemberProfile profile) throws SQLException {
        final String sql = "INSERT INTO member_profiles "
                + "(member_id, first_name, last_name, gender, weight, height, birthday, img_large, img_medium, img_small,weight_kg,weight_lbs,height_cm,height_inc) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                " on DUPLICATE KEY UPDATE member_id = VALUES(member_id), " +
                "first_name = VALUES(first_name), " +
                "last_name = VALUES(last_name), " +
                "gender = VALUES(gender)," +
                "weight = VALUES(weight)," +
                "birthday = VALUES(birthday)," +
                "img_large = VALUES(img_large)," +
                "img_medium = VALUES(img_medium)," +
                "img_small = VALUES(img_small) ," +
                "weight_kg = VALUES(weight_kg) ," +
                "weight_lbs = VALUES(weight_lbs) ," +
                "height_cm = VALUES(height_cm) ," +
                "height_inc = VALUES(height_inc) ";
        final Object[] params = new Object[]{profile.memberId, profile.firstName, profile.lastName, profile.gender,
                profile.weight, profile.height, profile.birthday, profile.imgLarge, profile.imgMedium, profile.imgSmall,
                profile.weightkg, profile.weightlbs, profile.heightcm, profile.heightinch};
        return t.update(sql, params);
    }

}
