package queryDsl.himedia.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import himedia.project.entity.Gender;
import himedia.project.entity.Pet;


/**
 * QPet is a Querydsl query type for Pet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPet extends EntityPathBase<Pet> {

    private static final long serialVersionUID = 656113332L;

    public static final QPet pet = new QPet("pet");

    public final StringPath address = createString("address");

    public final StringPath content = createString("content");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> petAge = createNumber("petAge", Integer.class);

    public final StringPath petName = createString("petName");

    public final StringPath phone = createString("phone");

    public final StringPath reservateDate = createString("reservateDate");

    public final StringPath userId = createString("userId");

    public QPet(String variable) {
        super(Pet.class, forVariable(variable));
    }

    public QPet(Path<? extends Pet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPet(PathMetadata metadata) {
        super(Pet.class, metadata);
    }

}

