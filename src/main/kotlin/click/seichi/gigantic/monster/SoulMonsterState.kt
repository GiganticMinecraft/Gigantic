package click.seichi.gigantic.monster

/**
 * @author tar0ss
 */
enum class SoulMonsterState {
    // 待機
    SEAL,
    // 目覚め
    WAKE,
    // 消滅
    DISAPPEAR,
    // 指示待機
    WAIT,
    // 移動
    MOVE,
    // 攻撃
    ATTACK,
    // 死亡
    DEATH;
}