private static double getDoubleNBTValue(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity=world.getBlockEntity(pos);
		if(blockEntity != null) return blockEntity.getPersistentData().getDouble(tag);
		return -1;
}