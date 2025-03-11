package team.lindo.backend;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

public class PackageDependencyCycleTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests()) // 테스트 코드 제외
            .withImportOption(new ImportOption.DoNotIncludeJars())  // 외부 라이브러리 제외
            .importPackages("team.lindo.backend.application");  // 위의 두 줄 적용하면 JPA, QueryDSL 같은 자동 생성 클래스 제외

    @Test
    void 패키지_사이클이_존재하면_실패() {
        ArchRule rule = SlicesRuleDefinition.slices()
                .matching("team.lindo.backend.application.(*)..") // 전체 패키지를 검사
                .should().beFreeOfCycles();

        rule.check(importedClasses);
    }
}
